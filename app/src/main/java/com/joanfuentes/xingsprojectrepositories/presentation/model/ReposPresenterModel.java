package com.joanfuentes.xingsprojectrepositories.presentation.model;

import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.ArrayList;
import java.util.List;

public class ReposPresenterModel {
    public static final int NO_ID = -1;
    public static final int DEFAULT_VISIBLE_THRESHOLD = 5;
    public static final int ROW_VIEW_TYPE_REPO = 0;
    public static final int ROW_VIEW_TYPE_PROGRESSBAR = 1;

    private int minimumPositionToLoad;
    private int visibleThreshold;
    private List<Repo> list;

    public ReposPresenterModel() {
        list = new ArrayList<>();
        visibleThreshold = DEFAULT_VISIBLE_THRESHOLD;
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
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
                && (minimumPositionToLoad + visibleThreshold > getLastItemIndexOfTheList());
    }

    public void clearMinimumPositionToLoad() {
        setMinimumPositionToLoad(0);
    }

    public void setMinimumPositionToLoad(int minimumPositionToLoad) {
        this.minimumPositionToLoad = minimumPositionToLoad;
    }

    public boolean existAMinimumPositionToLoad() {
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
        int lastItemIndex = getLastItemIndexOfTheList();
        if (!isTheListEmpty() && isARepo(lastItemIndex)) {
            added = list.add(null);
        }
        return added;
    }

    public boolean removeProgressBarFromTheList() {
        boolean removed = false;
        if (!isTheListEmpty()) {
            int lastItemIndex = getLastItemIndexOfTheList();
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

    private int getLastItemIndexOfTheList() {
        return getSizeList() - 1;
    }
}
