package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private static final int FIRST_PAGE_INDEX = 1;
    private static final int PROGRESS_BAR_ITEM = 1;
    private static final int VISIBLE_THRESHOLD = 5;
    private boolean loading;
    private int currentPage;
    private int previousTotalItemCount;
    private LinearLayoutManager layoutManager;
    private Callback onLoadMoreCallback;

    @Inject
    public EndlessScrollListener() {
        loading = true;
        currentPage = 1;
        previousTotalItemCount = 0;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        if (dy > 0) {
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
            int visibleItemCount = view.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = FIRST_PAGE_INDEX;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    loading = true;
                }
            }
            if (loading
                    && (totalItemCount > previousTotalItemCount + PROGRESS_BAR_ITEM)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
            }
            if (!loading
                    && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                loading = true;
                currentPage++;
                if (onLoadMoreCallback != null) {
                    onLoadMoreCallback.onLoadMore(currentPage);
                }
            }
        }
    }

    void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    void setOnLoadMoreCallback(Callback callback) {
        this.onLoadMoreCallback = callback;
    }

    void resetState() {
        this.currentPage = FIRST_PAGE_INDEX;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    void onLoadMoreCallbackFailed() {
        this.currentPage--;
        this.loading = false;
    }

    public interface Callback {
        void onLoadMore(int page);
    }
}
