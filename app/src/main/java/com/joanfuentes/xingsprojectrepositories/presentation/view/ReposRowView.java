package com.joanfuentes.xingsprojectrepositories.presentation.view;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

public interface ReposRowView {
    void setName(String name);
    void setOwner(String owner);
    void setDescription(String description);
    void setEmptyDescription();
    void showDescriptionBlock();
    void hideDescriptionBlock();
    void showNonDescriptionBlock();
    void hideNonDescriptionBlock();
    void setRepoAsForkable();
    void setRepoAsNotForkable();
    void setRepo(Repo repo);
}
