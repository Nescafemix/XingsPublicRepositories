package com.joanfuentes.xingsprojectrepositories.presentation.presenter;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.domain.usecase.GetReposUseCase;
import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ReposPresenter extends BasePresenter {
    private final PublicRepositoriesView publicRepositoriesView;
    private final GetReposUseCase getReposUseCase;
    private int minimumPositionToLoad;
    private List<Repo> savedRepos;

    @Inject
    public ReposPresenter(PublicRepositoriesView publicRepositoriesView, GetReposUseCase getReposUseCase) {
        this.publicRepositoriesView = publicRepositoriesView;
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
        if (publicRepositoriesView != null && publicRepositoriesView.isReady()) {
            publicRepositoriesView.showLoadingMoreProgress();
            getRepos();
        }
    }

    private void executeGetReposUseCase() {
        getReposUseCase.execute(new GetReposUseCase.Callback() {
            @Override
            public void onReposReady(List<Repo> repos) {
                if (publicRepositoriesView != null && publicRepositoriesView.isReady()) {
                    savedRepos.addAll(repos);
                    if (isNecessaryToLoadMoreData(repos.size())) {
                        getRepos();
                    } else {
                        minimumPositionToLoad = 0;
                        publicRepositoriesView.showList();
                        if (publicRepositoriesView.containsData()) {
                            publicRepositoriesView.hideLoadingMoreProgress();
                        } else {
                            publicRepositoriesView.hideLoadingProgress();
                        }
                        publicRepositoriesView.renderRepos(savedRepos);
                        savedRepos.clear();
                    }
                }
            }

            @Override
            public void onError() {
                if (publicRepositoriesView != null && publicRepositoriesView.isReady()) {
                    if (publicRepositoriesView.containsData()) {
                        publicRepositoriesView.hideLoadingMoreProgress();
                        publicRepositoriesView.renderSilentError();
                    } else {
                        publicRepositoriesView.hideList();
                        publicRepositoriesView.hideLoadingProgress();
                        publicRepositoriesView.renderError();
                    }
                }
            }
        });
    }

    private boolean isNecessaryToLoadMoreData(int repos) {
        return repos > 0
                && minimumPositionToLoad > 0
                && (minimumPositionToLoad + publicRepositoriesView.getVisibleThreshold() > savedRepos.size() - 1);
    }

    @Override
    public void onStop() {}

    public void forceRefresh() {
        if (publicRepositoriesView != null && publicRepositoriesView.isReady()) {
            savedRepos.clear();
            publicRepositoriesView.clear();
            publicRepositoriesView.showLoadingProgress();
            getReposUseCase.invalidateData();
            getRepos();
        }
    }

    public void restore(int minimumPositionToLoad) {
        this.minimumPositionToLoad = minimumPositionToLoad;
    }
}
