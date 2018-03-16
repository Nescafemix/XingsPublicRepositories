package com.joanfuentes.xingsprojectrepositories.presentation.view;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.List;

public interface PublicRepositoriesView {
    boolean isReady();
    void renderRepos(List<Repo> repos);
    void renderError();
}
