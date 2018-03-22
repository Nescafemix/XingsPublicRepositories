package com.joanfuentes.xingsprojectrepositories.presentation.view;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.ArrayList;
import java.util.List;

public class ReposPresenterModel {
    private static final int NO_ID = -1;
    static final int VISIBLE_THRESHOLD = 5;
    static final int ROW_VIEW_TYPE_REPO = 0;
    static final int ROW_VIEW_TYPE_PROGRESSBAR = 1;

    private int minimumPositionToLoad;
    private List<Repo> list;

    public ReposPresenterModel() {
        list = new ArrayList<>();
    }

    public void addReposToTheList(List<Repo> repos) {
        list.addAll(repos);
    }

    public List<Repo> getList() {
        return list;
    }

    public boolean isTheListEmpty() {
        return list.isEmpty();
    }
    
    public Repo getItemList(int position) {
        return list.get(position);
    }

    public int getSizeList() {
        return list.size();
    }

    public boolean needsMoreData(int numberOfLastReceivedRepos) {
        return numberOfLastReceivedRepos > 0
                && existAMinimumPositionToLoad()
                && (minimumPositionToLoad + VISIBLE_THRESHOLD > getSizeList() - 1);
    }

    public void clearMinimumPositionToLoad() {
        setMinimumPositionToLoad(0);
    }

    public void setMinimumPositionToLoad(int minimumPositionToLoad) {
        this.minimumPositionToLoad = minimumPositionToLoad;
    }

    private boolean existAMinimumPositionToLoad() {
        return minimumPositionToLoad > 0;
    }

    public int getItemListViewType(int position) {
        return isARepo(position) ? ROW_VIEW_TYPE_REPO : ROW_VIEW_TYPE_PROGRESSBAR;
    }

    private boolean isARepo(int position) {
        return getItemList(position) != null;
    }

    private boolean isAProgressBar(int position) {
        return getItemList(position) == null;
    }

    public boolean addProgressBarToTheList() {
        boolean added = false;
        int lastItemIndex = getSizeList() - 1;
        if (isARepo(lastItemIndex)) {
            added = list.add(null);
        }
        return added;
    }

    public boolean removeProgressBarFromTheList() {
        boolean removed = false;
        if (!isTheListEmpty()) {
            int lastItemIndex = getSizeList() - 1;
            if (lastItemIndex > 0 && isAProgressBar(lastItemIndex)) {
                list.remove(lastItemIndex);
                removed = true;
            }
        }
        return removed;
    }

    public long getItemListId(int position) {
        long id = NO_ID;
        if (getItemListViewType(position) == ROW_VIEW_TYPE_REPO) {
            id = getItemList(position).getHtmlUrl().hashCode();
        }
        return id;
    }

    public void clearList() {
        list.clear();
    }
}
