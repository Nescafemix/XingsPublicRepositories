package com.joanfuentes.xingsprojectrepositories.presentation.presenter;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.domain.usecase.GetReposUseCase;
import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesView;

import java.util.List;

import javax.inject.Inject;

public class ReposPresenter extends BasePresenter {
    private final PublicRepositoriesView publicRepositoriesView;
    private final GetReposUseCase getReposUseCase;

    @Inject
    public ReposPresenter(PublicRepositoriesView publicRepositoriesView, GetReposUseCase getReposUseCase) {
        this.publicRepositoriesView = publicRepositoriesView;
        this.getReposUseCase = getReposUseCase;
    }

    @Override
    public void onStart() {
        getRepos();
    }

    public void getRepos() {
        executeGetReposUseCase();
    }

    private void executeGetReposUseCase() {
        getReposUseCase.execute(new GetReposUseCase.Callback() {
            @Override
            public void onReposReady(List<Repo> repos) {
                if (publicRepositoriesView != null && publicRepositoriesView.isReady()) {
                    publicRepositoriesView.renderRepos(repos);
                }
            }

            @Override
            public void onError() {
                if (publicRepositoriesView != null && publicRepositoriesView.isReady()) {
                    publicRepositoriesView.renderError();
                }
            }
        });
    }

    @Override
    public void onStop() {}

    public void forceRefresh() {
        getReposUseCase.invalidateCaches();
        getRepos();
    }
}
