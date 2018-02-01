package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.List;

import javax.inject.Inject;

public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private static final int FIRST_PAGE_INDEX = 0;
    private static final int PROGRESS_BAR_ITEM = 1;
    private boolean loading = true;
    private int currentPage = 1;
    private int previousTotalItemCount = 0;
    private int visibleThreshold = 5;
    private LinearLayoutManager layoutManager;
    private Callback onLoadMoreCallback;

    @Inject
    public EndlessScrollListener() {}

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy){
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
                    && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                loading = true;
                currentPage++;
                if (onLoadMoreCallback != null) {
                    onLoadMoreCallback.onLoadMore(currentPage);
                }
            }
        }
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    public void setOnLoadMoreCallback(Callback callback) {
        this.onLoadMoreCallback = callback;
    }

    public void resetState() {
        this.currentPage = FIRST_PAGE_INDEX;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    public interface Callback {
        void onLoadMore(int page);
    }
}
