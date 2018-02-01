package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.joanfuentes.xingsprojectrepositories.Application;
import com.joanfuentes.xingsprojectrepositories.R;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.presentation.presenter.ReposPresenter;
import com.joanfuentes.xingsprojectrepositories.presentation.view.internal.di.DaggerRuntimeActivityComponent;
import com.joanfuentes.xingsprojectrepositories.presentation.view.internal.di.RuntimeActivityModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class PublicRepositoriesActivity extends BaseActivity {
    @BindView(R.id.item_list) RecyclerView recyclerView;
    @BindView(R.id.swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.initial_progressbar) ContentLoadingProgressBar contentLoadingProgressBar;
    private List<Repo> repos;
    private boolean pendingLoadMore = false;

    @Inject ReposPresenter presenter;
    @Inject ReposAdapter recyclerViewAdapter;
    @Inject EndlessScrollListener endlessScrollListener;
    @Inject DialogRepoDetail dialogRepoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    int onRequestLayout() {
        return R.layout.activity_public_repositories;
    }

    @Override
    void onInitializeInjection() {
        DaggerRuntimeActivityComponent
                .builder()
                .applicationComponent(((Application)getApplication()).getApplicationComponent())
                .runtimeActivityModule(new RuntimeActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    void onViewReady() {
        setSwipe2Refresh();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    private void setSwipe2Refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                forceRefresh();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void forceRefresh() {
        int size = repos.size();
        repos.clear();
        recyclerViewAdapter.notifyItemRangeRemoved(0, size);
        endlessScrollListener.resetState();
        presenter.forceRefresh();
    }

    public void renderRepos(List<Repo> repos) {
        showList();
        if (recyclerView.getAdapter() == null) {
            this.repos = new ArrayList<>(repos);
            setupFirstTimeRecyclerView(this.repos);
        } else {
            int lastItemIndex = this.repos.size() - 1;
            if (pendingLoadMore) {
                this.repos.remove(lastItemIndex);
                pendingLoadMore = false;
            }
            this.repos.addAll(repos);
            updateDataOnRecyclerView(lastItemIndex, repos.size());
        }
    }

    private void showList() {
        contentLoadingProgressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setupFirstTimeRecyclerView(List<Repo> repos) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerViewAdapter.setData(repos);
        recyclerViewAdapter.setOnItemLongClickListener(getItemLongClickListenerCallback());
        endlessScrollListener.setLinearLayoutManager(layoutManager);
        endlessScrollListener.setOnLoadMoreCallback(getEndlessScrollListenerCallback());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addOnScrollListener(endlessScrollListener);
    }

    @NonNull
    private ReposAdapter.Callback getItemLongClickListenerCallback() {
        return new ReposAdapter.Callback() {
            @Override
            public void onLongClick(Repo repo) {
                showDialogToOpenRepo(repo);
            }
        };
    }

    @NonNull
    private EndlessScrollListener.Callback getEndlessScrollListenerCallback() {
        return new EndlessScrollListener.Callback() {
            @Override
            public void onLoadMore(int page) {
                loadNextPage(page);
            }
        };
    }

    private void loadNextPage(final int page) {
        pendingLoadMore = true;
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                showLoadMoreProgressBar();
                presenter.getRepos(page);
            }
        });
    }

    private void showDialogToOpenRepo(Repo repo) {
        dialogRepoDetail.show(this, repo);
    }

    private void showLoadMoreProgressBar() {
        repos.add(null);
        recyclerViewAdapter.notifyItemInserted(repos.size() - 1);
    }

    private void updateDataOnRecyclerView(int index, int size) {
        recyclerViewAdapter.notifyItemRemoved(index);
        recyclerViewAdapter.notifyItemRangeInserted(index, size);
    }

    public void renderError() {
        View rootView = getWindow().getDecorView().getRootView();
        if (rootView != null) {
            Snackbar.make(rootView, R.string.threre_was_a_connectivity_error,
                    Snackbar.LENGTH_SHORT)
                    .show();
            hideRefreshIndicator();
        }
    }

    private void hideRefreshIndicator() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
