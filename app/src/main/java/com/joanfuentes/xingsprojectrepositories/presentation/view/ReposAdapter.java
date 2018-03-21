package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joanfuentes.xingsprojectrepositories.R;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.presentation.presenter.ReposPresenter;

public class ReposAdapter extends RecyclerView.Adapter {
    private Callback onItemLongClickListener;
    private ReposPresenter presenter;

    ReposAdapter(ReposPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ReposPresenter.ROW_VIEW_TYPE_REPO) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.repos_item, parent, false);
            viewHolder = new RepoViewHolder(view, onItemLongClickListener);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loading_more, parent, false);
            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReposRowView) {
            presenter.bindReposRowViewAtPosition(position, (ReposRowView) holder);
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getReposRowsCount();
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getReposRowViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return presenter.getReposRowItemId(position);
    }

    void setOnItemLongClickListener(Callback callback) {
        this.onItemLongClickListener = callback;
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressViewHolder(View view) {
            super(view);
        }
    }

    interface Callback {
        void onLongClick(Repo repo);
    }
}
