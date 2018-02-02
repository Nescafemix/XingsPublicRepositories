package com.joanfuentes.xingsprojectrepositories.data;

import com.joanfuentes.xingsprojectrepositories.UnitTest;
import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposCloudDataSource;
import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposLocalDataSource;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class ReposRepositoryUnitTest extends UnitTest{
    private static final int FIRST_PAGE = 1;
    private ReposRepository reposRepository;
    @Mock private ReposRepository.Callback reposCallbackMock;
    @Mock private ReposLocalDataSource reposLocalDataSource;
    @Mock private ReposCloudDataSource reposCloudDataSource;
    @Mock private Repo repoMock;
    private List<Repo> emptyRepos;
    private List<Repo> filledRepos;

    @Override
    protected void onSetup() {
        reposRepository = new ReposRepository(reposLocalDataSource, reposCloudDataSource);
        emptyRepos = new ArrayList<>();
        filledRepos = new ArrayList<>();
        filledRepos.add(repoMock);
    }

    @Test
    public void shouldGetReposFromLocalDataSource() {
        doReturn(filledRepos).when(reposLocalDataSource).getRepos(FIRST_PAGE);

        reposRepository.getRepos(FIRST_PAGE, reposCallbackMock);

        verify(reposLocalDataSource).getRepos(eq(FIRST_PAGE));
        verify(reposCallbackMock).onSuccess(eq(filledRepos));
    }

    @Test
    public void shouldGetReposFromCloudDataSource() {
        doReturn(emptyRepos).when(reposLocalDataSource).getRepos(FIRST_PAGE);
        doReturn(filledRepos).when(reposCloudDataSource).getRepos(FIRST_PAGE);

        reposRepository.getRepos(FIRST_PAGE, reposCallbackMock);

        verify(reposLocalDataSource).getRepos(eq(FIRST_PAGE));
        verify(reposCloudDataSource).getRepos(eq(FIRST_PAGE));
        verify(reposCallbackMock).onSuccess(eq(filledRepos));
    }

    @Test
    public void shouldInvalidateLocalDataSource() {
        reposRepository.invalidateCaches();

        verify(reposLocalDataSource).invalidate();
    }
}
