package com.joanfuentes.xingsprojectrepositories.presentation.presenter;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.domain.usecase.GetReposUseCase;
import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ReposPresenter extends BasePresenter {
    private final PublicRepositoriesView view;
    private final GetReposUseCase getReposUseCase;
    private int minimumPositionToLoad;
    private List<Repo> savedRepos;

    @Inject
    public ReposPresenter(PublicRepositoriesView view, GetReposUseCase getReposUseCase) {
        this.view = view;
        this.getReposUseCase = getReposUseCase;
        this.savedRepos = new ArrayList<>();
    }

    @Override
    public void onStart() {
        getRepos();
    }

    public void getRepos() {
        executeGetReposUseCase();
    }

    public void getMoreRepos() {
        if (view != null && view.isReady()) {
            int lastItemIndex = this.savedRepos.size() - 1;
            if (this.savedRepos.get(lastItemIndex) != null) {
                this.savedRepos.add(null);
                view.showLoadingMoreProgress();
            }
            getRepos();
        }
    }

    private void executeGetReposUseCase() {
        getReposUseCase.execute(new GetReposUseCase.Callback() {
            @Override
            public void onReposReady(List<Repo> repos) {
                if (view != null && view.isReady()) {
                    if (isNecessaryToLoadMoreData(repos.size())) {
                        savedRepos.addAll(repos);
                        getRepos();
                    } else {
                        minimumPositionToLoad = 0;
                        view.showList();
                        if (containsRepos()) {
                            hideLoadingMoreProgress();
                        } else {
                            view.hideLoadingProgress();
                        }
                        savedRepos.addAll(repos);
                        view.renderRepos(savedRepos, repos.size());
                    }
                }
            }

            @Override
            public void onError() {
                if (view != null && view.isReady()) {
                    if (containsRepos()) {
                        view.hideLoadingMoreProgress();
                        view.renderSilentError();
                    } else {
                        view.hideList();
                        view.hideLoadingProgress();
                        view.renderError();
                    }
                }
            }
        });
    }

    public boolean containsRepos() {
        return !this.savedRepos.isEmpty();
    }


    private void hideLoadingMoreProgress() {
        if (!this.savedRepos.isEmpty()) {
            int lastItemIndex = this.savedRepos.size() - 1;
            if (this.savedRepos.get(lastItemIndex) == null) {
                this.savedRepos.remove(lastItemIndex);
                view.hideLoadingMoreProgress();
            }
        }
    }

    private boolean isNecessaryToLoadMoreData(int repos) {
        return repos > 0
                && minimumPositionToLoad > 0
                && (minimumPositionToLoad + view.getVisibleThreshold() > savedRepos.size() - 1);
    }

    @Override
    public void onStop() {}

    public void forceRefresh() {
        if (view != null && view.isReady()) {
            savedRepos.clear();
            view.clear();
            view.showLoadingProgress();
            getReposUseCase.invalidateData();
            getRepos();
        }
    }

    public void restore(int minimumPositionToLoad) {
        this.minimumPositionToLoad = minimumPositionToLoad;
    }
}
