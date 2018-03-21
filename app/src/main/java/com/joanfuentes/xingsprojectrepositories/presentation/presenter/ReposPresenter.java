package com.joanfuentes.xingsprojectrepositories.presentation.presenter;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.domain.usecase.GetReposUseCase;
import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesView;
import com.joanfuentes.xingsprojectrepositories.presentation.view.ReposRowView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ReposPresenter extends BasePresenter {
    public static final int ROW_VIEW_TYPE_REPO = 0;
    public static final int ROW_VIEW_TYPE_PROGRESSBAR = 1;
    private static final int NO_ID = -1;


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

    public void bindReposRowViewAtPosition(int position, ReposRowView rowView) {
        Repo repo = savedRepos.get(position);
        if (repo != null) {
            rowView.setRepo(repo);
            rowView.setName(repo.getName());
            rowView.setOwner(repo.getOwnerLogin());
            if (repo.hasValidDescription()) {
                rowView.setDescription(repo.getDescription());
                rowView.showDescriptionBlock();
                rowView.hideNonDescriptionBlock();
            } else {
                rowView.setEmptyDescription();
                rowView.hideDescriptionBlock();
                rowView.showNonDescriptionBlock();
            }
            if (repo.isFork()) {
                rowView.setRepoAsForkable();
            } else {
                rowView.setRepoAsNotForkable();
            }
        }
    }

    public int getReposRowsCount() {
        return savedRepos.size();
    }

    public int getReposRowViewType(int position) {
        return savedRepos.get(position) != null ? ROW_VIEW_TYPE_REPO : ROW_VIEW_TYPE_PROGRESSBAR;
    }

    public long getReposRowItemId(int position) {
        long id = NO_ID;
        if (getReposRowViewType(position) == ROW_VIEW_TYPE_REPO) {
            id = savedRepos.get(position).getHtmlUrl().hashCode();
        }
        return id;
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

    private boolean containsRepos() {
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
