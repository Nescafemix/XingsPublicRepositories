package com.joanfuentes.xingsprojectrepositories.presentation.presenter;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.domain.usecase.GetReposUseCase;
import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesActivity;

import java.util.List;

import javax.inject.Inject;

public class ReposPresenter extends BasePresenter {
    private final PublicRepositoriesActivity activity;
    private final GetReposUseCase useCase;

    @Inject
    public ReposPresenter(PublicRepositoriesActivity activity, GetReposUseCase useCase) {
        this.activity = activity;
        this.useCase = useCase;
    }

    @Override
    public void onStart() {
        executeUseCase();
    }

    private void executeUseCase() {
        useCase.execute(new GetReposUseCase.Callback() {
            @Override
            public void onReposReady(List<Repo> repos) {
                activity.renderRepos(repos);
            }

            @Override
            public void onError() {
                activity.renderError();
            }
        });
    }

    @Override
    public void onStop() {}
}
