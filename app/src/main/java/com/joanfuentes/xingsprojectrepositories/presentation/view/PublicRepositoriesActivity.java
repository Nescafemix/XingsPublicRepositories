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

public class PublicRepositoriesActivity extends BaseActivity implements PublicRepositoriesView {
    private static final String FIRST_LIST_ELEMENT_OFFSET = "first_list_element_offset";
    private static final String FIRST_LIST_ELEMENT_POSITION = "first_list_element_position";
    private static final int VISIBLE_THRESHOLD = 5;
    private List<Repo> repos;
    private List<Repo> savedRepos;
    private boolean pendingLoadMore;
    private int positionToNavigate;
    private int offsetToApplyOnPositionToNavigate;
    private boolean pendingNavigateToPreviousPosition;
    private boolean pendingLoadNecessaryData;

    @BindView(R.id.item_list) RecyclerView recyclerView;
    @BindView(R.id.swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.initial_progressbar) ContentLoadingProgressBar contentLoadingProgressBar;
    @BindView(R.id.connectivity_error) View connectivityErrorView;

    @Inject ReposPresenter presenter;
    @Inject ReposAdapter recyclerViewAdapter;
    @Inject EndlessScrollListener endlessScrollListener;
    @Inject DialogRepoDetail dialogRepoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        repos = new ArrayList<>();
        savedRepos = new ArrayList<>();
        pendingLoadMore = false;
        pendingNavigateToPreviousPosition = false;
        pendingLoadNecessaryData = false;
        if (savedInstanceState != null) {
            positionToNavigate = savedInstanceState.getInt(FIRST_LIST_ELEMENT_POSITION);
            offsetToApplyOnPositionToNavigate = savedInstanceState.getInt(FIRST_LIST_ELEMENT_OFFSET);
            pendingNavigateToPreviousPosition = true;
            pendingLoadNecessaryData = true;
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(FIRST_LIST_ELEMENT_POSITION, getFirstVisibleItemPositionInTheList());
        outState.putInt(FIRST_LIST_ELEMENT_OFFSET, getFirstVisibleItemOffsetInTheList());
        super.onSaveInstanceState(outState);
    }

    private int getFirstVisibleItemOffsetInTheList() {
        int offset = 0;
        View startView = recyclerView.getChildAt(0);
        if (startView != null) {
            offset = startView.getTop() - recyclerView.getPaddingTop();
        }
        return offset;
    }

    private int getFirstVisibleItemPositionInTheList() {
        return ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
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
        presenter.forceRefresh();
    }

    @Override
    public void clear() {
        repos.clear();
        recyclerViewAdapter.notifyDataSetChanged();
        endlessScrollListener.resetState();
    }

    @Override
    public void showLoadingProgress() {
        contentLoadingProgressBar.setVisibility(View.VISIBLE);
        connectivityErrorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean isReady() {
        return !isFinishing();
    }

    @Override
    public void renderRepos(List<Repo> repos) {
        if (pendingLoadNecessaryData) {
            continueLoadingNecessaryData(repos);
        } else {
            showList();
            if (recyclerView.getAdapter() == null) {
                this.repos = new ArrayList<>(repos);
                setupFirstTimeRecyclerView(this.repos);
            } else {
                int lastItemIndex = 0;
                if (pendingLoadMore) {
                    lastItemIndex = removeLastItem();
                    pendingLoadMore = false;
                }
                this.repos.addAll(repos);
                recyclerViewAdapter.notifyItemRangeInserted(lastItemIndex, repos.size());
            }
        }
    }

    @Override
    public void renderError() {
        if (pendingLoadMore) {
            endlessScrollListener.onLoadMoreCallbackFailed();
            removeLastItem();
            pendingLoadMore = false;
        }
        if (repos.isEmpty()) {
            showConnectivityErrorMessage();
        } else {
            showConnectivityErrorMessageWithSnackBar();
        }
    }

    private void continueLoadingNecessaryData(List<Repo> repos) {
        savedRepos.addAll(repos);
        if (positionToNavigate + VISIBLE_THRESHOLD > savedRepos.size() - 1) {
            loadMoreData();
        } else {
            pendingLoadNecessaryData = false;
            renderRepos(savedRepos);
        }
    }

    private int removeLastItem() {
        int lastItemIndex = 0;
        if (!this.repos.isEmpty()) {
            lastItemIndex = this.repos.size() - 1;
            this.repos.remove(lastItemIndex);
            recyclerViewAdapter.notifyItemRemoved(lastItemIndex);
        }
        return lastItemIndex;
    }

    private void showList() {
        recyclerView.setVisibility(View.VISIBLE);
        connectivityErrorView.setVisibility(View.GONE);
        contentLoadingProgressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showConnectivityErrorMessage() {
        connectivityErrorView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        contentLoadingProgressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void setupFirstTimeRecyclerView(List<Repo> repos) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerViewAdapter.setData(repos);
        recyclerViewAdapter.setHasStableIds(true);
        recyclerViewAdapter.setOnItemLongClickListener(getItemLongClickListenerCallback());
        endlessScrollListener.setLinearLayoutManager(layoutManager);
        endlessScrollListener.setOnLoadMoreCallback(getEndlessScrollListenerCallback());
        endlessScrollListener.setVisibleThreshold(VISIBLE_THRESHOLD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addOnScrollListener(endlessScrollListener);
        if (pendingNavigateToPreviousPosition) {
            layoutManager.scrollToPositionWithOffset(positionToNavigate,
                    offsetToApplyOnPositionToNavigate);
        }
    }

    @NonNull
    private ReposAdapter.Callback getItemLongClickListenerCallback() {
        return new ReposAdapter.Callback() {
            @Override
            public void onLongClick(Repo repo) {
                showDialogWithRepoDetail(repo);
            }
        };
    }

    @NonNull
    private EndlessScrollListener.Callback getEndlessScrollListenerCallback() {
        return new EndlessScrollListener.Callback() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        };
    }

    private void loadMoreData() {
        pendingLoadMore = true;
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                showLoadMoreProgressBar();
                presenter.getRepos();
            }
        });
    }

    private void showDialogWithRepoDetail(Repo repo) {
        dialogRepoDetail.show(this, repo);
    }

    private void showLoadMoreProgressBar() {
        repos.add(null);
        recyclerViewAdapter.notifyItemInserted(repos.size() - 1);
    }

    private void showConnectivityErrorMessageWithSnackBar() {
        View rootView = getWindow().getDecorView().getRootView();
        if (rootView != null) {
            Snackbar.make(rootView, R.string.error_check_connectivity,
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
