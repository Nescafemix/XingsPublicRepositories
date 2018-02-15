package com.joanfuentes.xingsprojectrepositories.presentation.presenter;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.domain.usecase.GetReposUseCase;
import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesActivity;

import java.util.List;

import javax.inject.Inject;

public class ReposPresenter extends BasePresenter {
    private final PublicRepositoriesActivity activity;
    private final GetReposUseCase getReposUseCase;
    private static final int FIRST_PAGE = 1;

    @Inject
    public ReposPresenter(PublicRepositoriesActivity activity, GetReposUseCase getReposUseCase) {
        this.activity = activity;
        this.getReposUseCase = getReposUseCase;
    }

    @Override
    public void onStart() {
        getRepos(FIRST_PAGE);
    }

    public void getRepos(int page) {
        executeGetReposUseCase(page);
    }

    private void executeGetReposUseCase(int page) {
        getReposUseCase.execute(page, new GetReposUseCase.Callback() {
            @Override
            public void onReposReady(List<Repo> repos) {
                if (activity != null && !activity.isFinishing()) {
                    activity.renderRepos(repos);
                }
            }

            @Override
            public void onError() {
                if (activity != null && !activity.isFinishing()) {
                    activity.renderError();
                }
            }
        });
    }

    @Override
    public void onStop() {}

    public void forceRefresh() {
        getReposUseCase.invalidateCaches();
        getRepos(FIRST_PAGE);
    }
}
