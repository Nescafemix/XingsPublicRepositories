package com.joanfuentes.xingsprojectrepositories.presentation.presenter;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.domain.usecase.GetReposUseCase;
import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesView;
import com.joanfuentes.xingsprojectrepositories.presentation.model.ReposPresenterModel;
import com.joanfuentes.xingsprojectrepositories.presentation.view.ReposRowView;

import java.util.List;

import javax.inject.Inject;

public class ReposPresenter extends BasePresenter {
    private final PublicRepositoriesView view;
    private final GetReposUseCase getReposUseCase;
    private ReposPresenterModel reposPresenterModel;

    @Inject
    public ReposPresenter(PublicRepositoriesView view, GetReposUseCase getReposUseCase) {
        this.view = view;
        this.getReposUseCase = getReposUseCase;
        this.reposPresenterModel = new ReposPresenterModel();
    }

    @Override
    public void onStart() {
        getRepos();
    }

    public void bindReposRowViewAtPosition(int position, ReposRowView rowView) {
        Repo repo = reposPresenterModel.getItemList(position);
        if (repo != null) {
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
        return reposPresenterModel.getSizeList();
    }

    public int getReposRowViewType(int position) {
        return reposPresenterModel.getItemListViewType(position);
    }

    public long getReposRowItemId(int position) {
        return reposPresenterModel.getItemListId(position);
    }

    public void getRepos() {
        executeGetReposUseCase();
    }

    public void getMoreRepos() {
        if (view != null && view.isReady()) {
            if (reposPresenterModel.addProgressBarToTheList()) {
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
                    if (reposPresenterModel.needsMoreData(repos.size())) {
                        reposPresenterModel.addReposToTheList(repos);
                        getRepos();
                    } else {
                        reposPresenterModel.clearMinimumPositionToLoad();
                        view.hideError();
                        hideLoadingProgressViews();
                        reposPresenterModel.addReposToTheList(repos);
                        view.renderRepos(reposPresenterModel.getSizeList(), repos.size());
                    }
                }
            }

            @Override
            public void onError() {
                if (view != null && view.isReady()) {
                    if (reposPresenterModel.isTheListEmpty()) {
                        view.hideRepos();
                        view.hideLoadingProgress();
                        view.showError();
                    } else {
                        view.hideLoadingMoreProgress();
                        view.showSilentError();
                    }
                }
            }
        });
    }

    private void hideLoadingProgressViews() {
        if (reposPresenterModel.isTheListEmpty()) {
            view.hideLoadingProgress();
        } else if (reposPresenterModel.removeProgressBarFromTheList()) {
            view.hideLoadingMoreProgress();
        }
    }

    @Override
    public void onStop() {}

    public void forceRefresh() {
        if (view != null && view.isReady()) {
            reposPresenterModel.clearList();
            view.clear();
            view.showLoadingProgress();
            getReposUseCase.invalidateData();
            getRepos();
        }
    }

    public void restoreMiniumPositionToLoad(int minimumPositionToLoad) {
        reposPresenterModel.setMinimumPositionToLoad(minimumPositionToLoad);
    }

    public void onItemListLongClick(int itemPosition) {
        view.showRepoDetail(reposPresenterModel.getItemList(itemPosition));
    }
}
