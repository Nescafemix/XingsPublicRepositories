package com.joanfuentes.xingsprojectrepositories.presentation.presenter;

import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesActivity;

import javax.inject.Inject;

public class ReposPresenter extends BasePresenter {
    private final PublicRepositoriesActivity activity;

    @Inject
    public ReposPresenter(PublicRepositoriesActivity activity) {
        this.activity = activity;
        //TODO: inject here the getReposUsecase
    }

    @Override
    public void onStart() {
        executeUseCase();
    }

    private void executeUseCase() {
        //TODO: implement here the call to execute from the specified usecase
    }

    @Override
    public void onStop() {}
}
