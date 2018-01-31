package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private static final int FIRST_PAGE_INDEX = 0;
    private boolean loading = true;
    private int currentPage = 1;
    private int previousTotalItemCount = 0;
    private LinearLayoutManager layoutManager;

    public EndlessScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy){
        if (dy > 0) {
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            int totalItemCount = layoutManager.getItemCount();
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = FIRST_PAGE_INDEX;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    loading = true;
                }
            }
            if (loading
                    && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
            }
            if (!loading
                    && (lastVisibleItemPosition + 1) >= totalItemCount) {
                currentPage++;
                loading = onLoadMore(currentPage);
            }
        }
    }

    public void resetState() {
        this.currentPage = FIRST_PAGE_INDEX;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    public abstract boolean onLoadMore(int page);
}
