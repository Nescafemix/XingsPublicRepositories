package com.joanfuentes.xingsprojectrepositories.presentation.model;

import com.joanfuentes.xingsprojectrepositories.UnitTest;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ReposPresenterModelTest extends UnitTest {
    private static final String SOME_HTML_URL = "http://www.trolololololololololo.com";
    private static final Repo SOME_REPO = new Repo.Builder().setHtmlUrl(SOME_HTML_URL).build();
    private static final ArrayList<Repo> SOME_REPOS =
            new ArrayList<>(Arrays.asList(SOME_REPO, SOME_REPO, SOME_REPO));
    private static final ArrayList<Repo> NO_REPOS = new ArrayList<>();
    private static final int SOME_NUMBER_OF_LAST_RECEIVED_REPOS = 3;
    private static final int NO_RECEIVED_REPOS = 0;
    private static final int SOME_NUMBER_OF_MINIMUM_POSITION_TO_LOAD = 5;
    private static final int LOWEST_POSITIVE_MINIMUM_POSITION_TO_LOAD = 1;
    private static final int SOME_THRESHOLD = 1;
    private static final int NO_MINIMUM_POSITION_TO_LOAD = 0;
    private static final int FIRST_POSITION_IN_A_LIST = 0;

    @Override
    protected void onSetup() {}

    @Test
    public void shouldBeTheListEmpty() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();

        boolean isEmpty = reposPresenterModel.isTheListEmpty();

        assertTrue(isEmpty);
    }

    @Test
    public void shouldBeTheListEmptyBecauseSetEmptyList() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(NO_REPOS);

        boolean isEmpty = reposPresenterModel.isTheListEmpty();

        assertTrue(isEmpty);
    }

    @Test
    public void shouldNotBeTheListEmpty() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);

        boolean isEmpty = reposPresenterModel.isTheListEmpty();

        assertFalse(isEmpty);
    }

    @Test
    public void shouldNeedsMoreData() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);
        reposPresenterModel.setMinimumPositionToLoad(SOME_NUMBER_OF_MINIMUM_POSITION_TO_LOAD);

        boolean needsMoreData = reposPresenterModel.needsMoreData(SOME_NUMBER_OF_LAST_RECEIVED_REPOS);

        assertTrue(needsMoreData);
    }

    @Test
    public void shouldNotNeedsMoreDataBecauseNoThresholdDataIsContainedOnCurrentData() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.setVisibleThreshold(SOME_THRESHOLD);
        reposPresenterModel.addReposToTheList(SOME_REPOS);
        reposPresenterModel.setMinimumPositionToLoad(LOWEST_POSITIVE_MINIMUM_POSITION_TO_LOAD);

        boolean needsMoreData = reposPresenterModel.needsMoreData(SOME_NUMBER_OF_LAST_RECEIVED_REPOS);

        assertFalse(needsMoreData);
    }

    @Test
    public void shouldNotNeedsMoreDataBecauseNoReceivedNewRepos() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);
        reposPresenterModel.setMinimumPositionToLoad(SOME_NUMBER_OF_MINIMUM_POSITION_TO_LOAD);

        boolean needsMoreData = reposPresenterModel.needsMoreData(NO_RECEIVED_REPOS);

        assertFalse(needsMoreData);
    }

    @Test
    public void shouldNotNeedsMoreDataBecauseNoMinimumPositionToLoad() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);
        reposPresenterModel.setMinimumPositionToLoad(NO_MINIMUM_POSITION_TO_LOAD);

        boolean needsMoreData = reposPresenterModel.needsMoreData(SOME_NUMBER_OF_MINIMUM_POSITION_TO_LOAD);

        assertFalse(needsMoreData);
    }

    @Test
    public void shouldExistAMinimumPositionToLoad() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.setMinimumPositionToLoad(SOME_NUMBER_OF_MINIMUM_POSITION_TO_LOAD);

        boolean existAMinimumPositionToLoad = reposPresenterModel.existAMinimumPositionToLoad();

        assertTrue(existAMinimumPositionToLoad);
    }

    @Test
    public void shouldNotExistAMinimumPositionToLoad() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.setMinimumPositionToLoad(NO_MINIMUM_POSITION_TO_LOAD);

        boolean existAMinimumPositionToLoad = reposPresenterModel.existAMinimumPositionToLoad();

        assertFalse(existAMinimumPositionToLoad);
    }

    @Test
    public void shouldReturnARepoViewType() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);

        int viewType = reposPresenterModel.getItemListViewType(FIRST_POSITION_IN_A_LIST);

        assertEquals(ReposPresenterModel.ROW_VIEW_TYPE_REPO, viewType);
    }

    @Test
    public void shouldReturnAProgressBarType() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);
        reposPresenterModel.addProgressBarToTheList();

        int viewType = reposPresenterModel.getItemListViewType(reposPresenterModel.getSizeList() - 1);

        assertEquals(ReposPresenterModel.ROW_VIEW_TYPE_PROGRESSBAR, viewType);
    }

    @Test
    public void shouldAddProgressBarToTheList() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);

        boolean added = reposPresenterModel.addProgressBarToTheList();

        assertTrue(added);
    }

    @Test
    public void shouldNotAddProgressBarToTheListBecauseIsEmpty() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();

        boolean added = reposPresenterModel.addProgressBarToTheList();

        assertFalse(added);
    }

    @Test
    public void shouldNotAddProgressBarToTheListBecauseIsAlreadyAdded() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);
        reposPresenterModel.addProgressBarToTheList();

        boolean added = reposPresenterModel.addProgressBarToTheList();

        assertFalse(added);
    }

    @Test
    public void shouldRemoveProgressBarFromTheList() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);
        reposPresenterModel.addProgressBarToTheList();

        boolean removed = reposPresenterModel.removeProgressBarFromTheList();

        assertTrue(removed);
    }

    @Test
    public void shouldNotRemoveProgressBarFromTheListBecauseListIsEmpty() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();

        boolean removed = reposPresenterModel.removeProgressBarFromTheList();

        assertFalse(removed);
    }

    @Test
    public void shouldNotRemoveProgressBarFromTheListBecauseThereIsNoProgressBar() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);

        boolean removed = reposPresenterModel.removeProgressBarFromTheList();

        assertFalse(removed);
    }

    @Test
    public void shouldGetItemListID() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);

        long itemListId = reposPresenterModel.getItemListId(FIRST_POSITION_IN_A_LIST);

        assertNotEquals(ReposPresenterModel.NO_ID, itemListId);
    }

    @Test
    public void shouldGetItemListNOID() {
        ReposPresenterModel reposPresenterModel = new ReposPresenterModel();
        reposPresenterModel.addReposToTheList(SOME_REPOS);
        reposPresenterModel.addProgressBarToTheList();

        long itemListId = reposPresenterModel.getItemListId(reposPresenterModel.getSizeList() - 1);

        assertEquals(ReposPresenterModel.NO_ID, itemListId);
    }
}
