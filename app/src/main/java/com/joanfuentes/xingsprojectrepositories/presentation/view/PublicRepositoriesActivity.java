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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class PublicRepositoriesActivity extends BaseActivity implements PublicRepositoriesView {
    private static final String FIRST_LIST_ELEMENT_OFFSET = "first_list_element_offset";
    private static final String FIRST_LIST_ELEMENT_POSITION = "first_list_element_position";
    private static final int VISIBLE_THRESHOLD = 5;
    private int positionToNavigate;
    private int offsetToApplyOnPositionToNavigate;
    private boolean pendingNavigateToPreviousPosition;
    private Snackbar errorSnackBar;

    @BindView(R.id.item_list) RecyclerView recyclerView;
    @BindView(R.id.swipe_container) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.initial_progressbar) ContentLoadingProgressBar contentLoadingProgressBar;
    @BindView(R.id.connectivity_error) View connectivityErrorView;

    @Inject ReposPresenter presenter;
    @Inject EndlessScrollListener endlessScrollListener;
    @Inject DialogRepoDetail dialogRepoDetail;
    ReposAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pendingNavigateToPreviousPosition = false;
        if (savedInstanceState != null) {
            positionToNavigate = savedInstanceState.getInt(FIRST_LIST_ELEMENT_POSITION);
            offsetToApplyOnPositionToNavigate = savedInstanceState.getInt(FIRST_LIST_ELEMENT_OFFSET);
            pendingNavigateToPreviousPosition = true;
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
        presenter.restore(positionToNavigate);
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
    public void renderRepos(List<Repo> repos, int numberOfNewItems) {
        if (recyclerView.getAdapter() == null) {
            setupFirstTimeRecyclerView(repos);
        } else {
            int positionStart = repos.size() - numberOfNewItems;
            recyclerViewAdapter.notifyItemRangeInserted(positionStart, numberOfNewItems);
        }
    }

    @Override
    public void renderError() {
        showConnectivityErrorMessage();
    }

    @Override
    public void renderSilentError() {
        endlessScrollListener.onLoadMoreCallbackFailed();
        showConnectivityErrorMessageWithSnackBar();
    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideError() {
        connectivityErrorView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoadingProgress() {
        contentLoadingProgressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public int getVisibleThreshold() {
        return VISIBLE_THRESHOLD;
    }

    private void showConnectivityErrorMessage() {
        connectivityErrorView.setVisibility(View.VISIBLE);
    }

    private void setupFirstTimeRecyclerView(List<Repo> repos) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerViewAdapter = new ReposAdapter(presenter);
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
                presenter.getMoreRepos();
            }
        };
    }

    private void showDialogWithRepoDetail(Repo repo) {
        dialogRepoDetail.show(this, repo);
    }

    public void showLoadingMoreProgress() {
        recyclerViewAdapter.notifyItemInserted(recyclerViewAdapter.getItemCount() - 1);
    }

    public void hideLoadingMoreProgress() {
        recyclerViewAdapter.notifyItemRemoved(recyclerViewAdapter.getItemCount());
    }

    private void showConnectivityErrorMessageWithSnackBar() {
        View rootView = getWindow().getDecorView().getRootView();
        if (rootView != null) {
            if (errorSnackBar == null) {
                errorSnackBar = Snackbar.make(rootView, R.string.error_check_connectivity,
                        Snackbar.LENGTH_SHORT);
            }
            if (!errorSnackBar.isShown()) {
                errorSnackBar.show();
            }
        }
    }
}
