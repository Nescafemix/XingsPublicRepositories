package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joanfuentes.xingsprojectrepositories.R;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {
    private List<Repo> repos;

    @Inject
    public ReposAdapter() {}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repos_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.repo = repos.get(position);
        holder.name.setText(holder.repo.getName());
        holder.owner.setText(holder.repo.getOwnerLogin());
        holder.description.setText(holder.repo.getDescription());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void setData(List<Repo> repos) {
        this.repos = repos;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nameTextView) TextView name;
        @BindView(R.id.ownerTextView) TextView owner;
        @BindView(R.id.descriptionTextView) TextView description;

        private Repo repo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}
