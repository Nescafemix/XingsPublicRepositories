package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joanfuentes.xingsprojectrepositories.R;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

import static android.support.v7.widget.RecyclerView.NO_ID;

public class ReposAdapter extends RecyclerView.Adapter {
    private static final String EMPTY_STRING = "";
    private static final int VIEW_TYPE_REPO = 0;
    private static final int VIEW_TYPE_PROGRESSBAR = 1;
    private Callback onItemLongClickListener;
    private List<Repo> repos;

    @Inject
    public ReposAdapter() {
        repos = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_TYPE_REPO) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.repos_item, parent, false);
            viewHolder = new RepoViewHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_more, parent, false);
            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RepoViewHolder) {
            RepoViewHolder repoViewHolder = (RepoViewHolder) holder;
            repoViewHolder.repo = repos.get(position);
            repoViewHolder.name.setText(repoViewHolder.repo.getName());
            repoViewHolder.owner.setText(repoViewHolder.repo.getOwnerLogin());
            if (repoViewHolder.repo.hasValidDescription()) {
                repoViewHolder.description.setText(repoViewHolder.repo.getDescription());
                repoViewHolder.existDescriptionView.setVisibility(View.VISIBLE);
                repoViewHolder.notExistDescriptionView.setVisibility(View.GONE);
            } else {
                repoViewHolder.description.setText(EMPTY_STRING);
                repoViewHolder.existDescriptionView.setVisibility(View.GONE);
                repoViewHolder.notExistDescriptionView.setVisibility(View.VISIBLE);
            }
            int resourceId = R.drawable.repo_not_forkable_row_background;
            if (repoViewHolder.repo.isFork()) {
                resourceId = R.drawable.repo_forkable_row_background;
            }
            repoViewHolder.itemView.setBackgroundResource(resourceId);
        }
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return repos.get(position) != null ? VIEW_TYPE_REPO : VIEW_TYPE_PROGRESSBAR;
    }

    @Override
    public long getItemId(int position) {
        long id = NO_ID;
        if (getItemViewType(position) == VIEW_TYPE_REPO) {
            id = repos.get(position).getHtmlUrl().hashCode();
        }
        return id;
    }

    public void setData(List<Repo> repos) {
        this.repos = repos;
    }

    void setOnItemLongClickListener(Callback callback) {
        this.onItemLongClickListener = callback;
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.existDescriptionView) View existDescriptionView;
        @BindView(R.id.notExistDescriptionView) View notExistDescriptionView;
        @BindView(R.id.nameTextView) TextView name;
        @BindView(R.id.ownerTextView) TextView owner;
        @BindView(R.id.descriptionTextView) TextView description;

        private Repo repo;

        RepoViewHolder(View view) {
            super(view);
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
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View view) {
            super(view);
        }
    }

    interface Callback {
        void onLongClick(Repo repo);
    }
}
