package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joanfuentes.xingsprojectrepositories.R;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

class RepoViewHolder extends RecyclerView.ViewHolder implements ReposRowView {
    private static final String EMPTY_STRING = "";
    private ReposAdapter.Callback onItemLongClickListener;
    private Repo repo;

    @BindView(R.id.existDescriptionView) View existDescriptionView;
    @BindView(R.id.notExistDescriptionView) View notExistDescriptionView;
    @BindView(R.id.nameTextView) TextView name;
    @BindView(R.id.ownerTextView) TextView owner;
    @BindView(R.id.descriptionTextView) TextView description;

    RepoViewHolder(View view, ReposAdapter.Callback onItemLongClickListener) {
        super(view);
        this.onItemLongClickListener = onItemLongClickListener;
        ButterKnife.bind(this, view);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + name.getText() + "'";
    }

    @OnLongClick()
    public boolean clickedItemList(View view) {
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onLongClick(repo);
        }
        return true;
    }

    @Override
    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    @Override
    public void setName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setOwner(String owner) {
        this.owner.setText(owner);
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void setEmptyDescription() {
        this.description.setText(EMPTY_STRING);
    }

    @Override
    public void showDescriptionBlock() {
        this.existDescriptionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDescriptionBlock() {
        this.existDescriptionView.setVisibility(View.GONE);
    }

    @Override
    public void showNonDescriptionBlock() {
        this.notExistDescriptionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNonDescriptionBlock() {
        this.notExistDescriptionView.setVisibility(View.GONE);
    }

    @Override
    public void setRepoAsForkable() {
        this.itemView.setBackgroundResource(R.drawable.repo_forkable_row_background);
    }

    @Override
    public void setRepoAsNotForkable() {
        this.itemView.setBackgroundResource(R.drawable.repo_not_forkable_row_background);
    }
}