package com.joanfuentes.xingsprojectrepositories.presentation.presenter;

import com.joanfuentes.xingsprojectrepositories.UnitTest;
import com.joanfuentes.xingsprojectrepositories.data.ReposRepository;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;
import com.joanfuentes.xingsprojectrepositories.domain.usecase.GetReposUseCase;
import com.joanfuentes.xingsprojectrepositories.presentation.model.ReposPresenterModel;
import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesView;
import com.joanfuentes.xingsprojectrepositories.presentation.view.ReposRowView;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReposPresenterTest extends UnitTest {
    private static final String SOME_HTML_URL = "http://www.trolololololololololo.com";
    private static final Repo SOME_REPO = new Repo.Builder().setHtmlUrl(SOME_HTML_URL).build();
    private static final ArrayList<Repo> SOME_REPOS =
            new ArrayList<>(Arrays.asList(SOME_REPO, SOME_REPO, SOME_REPO));
    private static final ArrayList<Repo> SOME_MORE_REPOS =
            new ArrayList<>(Arrays.asList(SOME_REPO, SOME_REPO));
    private static final ArrayList<Repo> NO_REPOS = new ArrayList<>();
    private static final int SOME_ITEM_POSITION = 2;
    private static final int SOME_NUMBER_OF_LAST_RECEIVED_REPOS = 3;
    private static final int NO_RECEIVED_REPOS = 0;
    private static final int SOME_NUMBER_OF_MINIMUM_POSITION_TO_LOAD = 5;
    private static final int LOWEST_POSITIVE_MINIMUM_POSITION_TO_LOAD = 1;
    private static final int SOME_THRESHOLD = 1;
    private static final int NO_MINIMUM_POSITION_TO_LOAD = 0;
    private static final int FIRST_POSITION_IN_A_LIST = 0;
    private static final boolean FORKABLE = true;
    private static final boolean NOT_FORKABLE = false;
    private static final boolean VALID = true;
    private static final boolean INVALID = false;
    private static final boolean READY = true;
    private static final boolean NOT_READY = false;
    private static final String SOME_DESCRIPTION = "some description";
    @Mock PublicRepositoriesView view;
    @Mock GetReposUseCase getReposUseCase;
    @Mock ReposRowView reposRowView;
    @Mock ReposPresenterModel reposPresenterModel;
    @Mock Repo repo;
    @Captor private ArgumentCaptor<GetReposUseCase.Callback> getReposUseCaseCallbackCaptor;
    private ReposPresenter reposPresenter;

    @Override
    protected void onSetup() {
        reposPresenter = new ReposPresenter(view, getReposUseCase);
    }

    @Test
    public void shouldBindRepoWithDescription() {
        doReturn(VALID).when(repo).hasValidDescription();
        doReturn(SOME_DESCRIPTION).when(repo).getDescription();
        doReturn(repo).when(reposPresenterModel).getItemList(FIRST_POSITION_IN_A_LIST);

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.bindReposRowViewAtPosition(FIRST_POSITION_IN_A_LIST, reposRowView);

        verify(reposRowView).setDescription(eq(SOME_DESCRIPTION));
        verify(reposRowView).showDescriptionBlock();
        verify(reposRowView).hideNonDescriptionBlock();
    }

    @Test
    public void shouldBindRepoWithoutDescription() {
        doReturn(INVALID).when(repo).hasValidDescription();
        doReturn(repo).when(reposPresenterModel).getItemList(FIRST_POSITION_IN_A_LIST);

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.bindReposRowViewAtPosition(FIRST_POSITION_IN_A_LIST, reposRowView);

        verify(reposRowView).setEmptyDescription();
        verify(reposRowView).hideDescriptionBlock();
        verify(reposRowView).showNonDescriptionBlock();
    }

    @Test
    public void shouldBindForkableRepo() {
        doReturn(FORKABLE).when(repo).isFork();
        doReturn(repo).when(reposPresenterModel).getItemList(FIRST_POSITION_IN_A_LIST);

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.bindReposRowViewAtPosition(FIRST_POSITION_IN_A_LIST, reposRowView);

        verify(reposRowView).setRepoAsForkable();
    }

    @Test
    public void shouldBindNonForkableRepo() {
        doReturn(NOT_FORKABLE).when(repo).isFork();
        doReturn(repo).when(reposPresenterModel).getItemList(FIRST_POSITION_IN_A_LIST);

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.bindReposRowViewAtPosition(FIRST_POSITION_IN_A_LIST, reposRowView);

        verify(reposRowView).setRepoAsNotForkable();
    }

    @Test
    public void shouldGetMoreReposShowingProgressBarInTheList() {
        doReturn(READY).when(view).isReady();
        doReturn(TRUE).when(reposPresenterModel).addProgressBarToTheList();
        doReturn(FALSE).when(reposPresenterModel).needsMoreData(eq(SOME_REPOS.size()));
        doReturn(FALSE).when(reposPresenterModel).isTheListEmpty();
        doReturn(TRUE).when(reposPresenterModel).removeProgressBarFromTheList();

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.getMoreRepos();

        verify(getReposUseCase, times(1)).execute(getReposUseCaseCallbackCaptor.capture());

        getReposUseCaseCallbackCaptor.getValue().onReposReady(SOME_REPOS);

        verify(view).showLoadingMoreProgress();
        verify(reposPresenterModel).clearMinimumPositionToLoad();
        verify(view).hideError();
        verify(view).hideLoadingMoreProgress();
        verify(reposPresenterModel).addReposToTheList(eq(SOME_REPOS));
        verify(view).renderRepos(any(Integer.class), eq(SOME_REPOS.size()));
    }

    @Test
    public void shouldGetMoreReposNotShowingProgressBarInTheList() {
        doReturn(READY).when(view).isReady();
        doReturn(FALSE).when(reposPresenterModel).addProgressBarToTheList();
        doReturn(FALSE).when(reposPresenterModel).needsMoreData(eq(SOME_REPOS.size()));
        doReturn(FALSE).when(reposPresenterModel).isTheListEmpty();
        doReturn(FALSE).when(reposPresenterModel).removeProgressBarFromTheList();

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.getMoreRepos();

        verify(getReposUseCase, times(1)).execute(getReposUseCaseCallbackCaptor.capture());

        getReposUseCaseCallbackCaptor.getValue().onReposReady(SOME_REPOS);

        verify(reposPresenterModel).clearMinimumPositionToLoad();
        verify(view).hideError();
        verify(reposPresenterModel).addReposToTheList(eq(SOME_REPOS));
        verify(view).renderRepos(any(Integer.class), eq(SOME_REPOS.size()));
    }

    @Test
    public void shouldGetFirstBlockOfRepos() {
        doReturn(READY).when(view).isReady();
        doReturn(FALSE).when(reposPresenterModel).needsMoreData(eq(SOME_REPOS.size()));

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.getRepos();

        verify(getReposUseCase, times(1)).execute(getReposUseCaseCallbackCaptor.capture());

        getReposUseCaseCallbackCaptor.getValue().onReposReady(SOME_REPOS);

        verify(reposPresenterModel).clearMinimumPositionToLoad();
        verify(view).hideError();
        verify(reposPresenterModel).addReposToTheList(eq(SOME_REPOS));
        verify(view).renderRepos(any(Integer.class), eq(SOME_REPOS.size()));
    }

    @Test
    public void shouldGetNecessaryRepos() {
        doReturn(READY).when(view).isReady();
        when(reposPresenterModel.needsMoreData(any(Integer.class))).thenReturn(TRUE, FALSE);

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.getRepos();

        verify(getReposUseCase, times(1)).execute(getReposUseCaseCallbackCaptor.capture());

        getReposUseCaseCallbackCaptor.getValue().onReposReady(SOME_REPOS);

        verify(reposPresenterModel).addReposToTheList(eq(SOME_REPOS));

        verify(getReposUseCase, times(2)).execute(getReposUseCaseCallbackCaptor.capture());

        getReposUseCaseCallbackCaptor.getValue().onReposReady(SOME_MORE_REPOS);

        verify(reposPresenterModel).clearMinimumPositionToLoad();
        verify(view).hideError();
        verify(reposPresenterModel).addReposToTheList(eq(SOME_MORE_REPOS));
        verify(view).renderRepos(any(Integer.class), eq(SOME_MORE_REPOS.size()));
    }

    @Test
    public void shouldGetErrorWithEmptyList() {
        doReturn(READY).when(view).isReady();
        doReturn(TRUE).when(reposPresenterModel).isTheListEmpty();

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.getMoreRepos();

        verify(getReposUseCase, times(1)).execute(getReposUseCaseCallbackCaptor.capture());

        getReposUseCaseCallbackCaptor.getValue().onError();

        verify(view).hideRepos();
        verify(view).hideLoadingProgress();
        verify(view).showError();
    }

    @Test
    public void shouldGetErrorWithFilledList() {
        doReturn(READY).when(view).isReady();
        doReturn(FALSE).when(reposPresenterModel).isTheListEmpty();
        doReturn(TRUE).when(reposPresenterModel).removeProgressBarFromTheList();

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.getMoreRepos();

        verify(getReposUseCase, times(1)).execute(getReposUseCaseCallbackCaptor.capture());

        getReposUseCaseCallbackCaptor.getValue().onError();

        verify(view).hideLoadingMoreProgress();
        verify(view).showSilentError();
    }

    @Test
    public void shouldForceRefresh() {
        doReturn(READY).when(view).isReady();
        doReturn(TRUE).when(reposPresenterModel).isTheListEmpty();

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.forceRefresh();

        verify(reposPresenterModel).clearList();
        verify(view).clear();
        verify(view).showLoadingProgress();
        verify(getReposUseCase).invalidateData();
        verify(getReposUseCase).execute(any(GetReposUseCase.Callback.class));
    }

    @Test
    public void shouldShowItemDetailOnItemListLongClick() {
        doReturn(READY).when(view).isReady();
        doReturn(SOME_REPO).when(reposPresenterModel).getItemList(eq(SOME_ITEM_POSITION));

        reposPresenter.setPresenterModel(reposPresenterModel);
        reposPresenter.onItemListLongClick(SOME_ITEM_POSITION);

        verify(view).showRepoDetail(eq(SOME_REPO));
    }

}