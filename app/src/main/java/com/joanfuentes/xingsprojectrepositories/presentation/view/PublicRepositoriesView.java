package com.joanfuentes.xingsprojectrepositories.presentation.view;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

public interface PublicRepositoriesView {
    boolean isReady();
    void renderRepos(int numberOfItems, int numberOfNewItems);
    void hideRepos();
    void showError();
    void hideError();
    void showSilentError();
    void clear();
    void showLoadingProgress();
    void hideLoadingProgress();
    void showLoadingMoreProgress();
    void hideLoadingMoreProgress();
    void showRepoDetail(Repo repo);
}
