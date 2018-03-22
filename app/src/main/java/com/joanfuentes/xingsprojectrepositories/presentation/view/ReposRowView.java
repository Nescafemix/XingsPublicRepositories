package com.joanfuentes.xingsprojectrepositories.presentation.view;

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
}
