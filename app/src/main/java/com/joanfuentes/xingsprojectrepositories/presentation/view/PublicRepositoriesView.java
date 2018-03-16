package com.joanfuentes.xingsprojectrepositories.presentation.view;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.List;

public interface PublicRepositoriesView {
    boolean isReady();
    void renderRepos(List<Repo> repos);
    void renderError();
    void renderSilentError();
    void hideError();
    void clear();
    void showList();
    void hideList();
    void showLoadingProgress();
    void hideLoadingProgress();
    int getVisibleThreshold();
    void showLoadingMoreProgress();
    void hideLoadingMoreProgress();
    boolean containsData();
}
