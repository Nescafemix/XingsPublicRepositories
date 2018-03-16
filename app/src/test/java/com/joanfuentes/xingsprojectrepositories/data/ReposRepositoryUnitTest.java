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
    private static final int FIRST_OFFSET_BLOCK = 1;
    private static final int DATA_BLOCK_SIZE = 10;
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
        doReturn(filledRepos).when(reposLocalDataSource).getRepos(FIRST_OFFSET_BLOCK);

        reposRepository.getRepos(reposCallbackMock);

        verify(reposLocalDataSource).getRepos(eq(FIRST_OFFSET_BLOCK));
        verify(reposCallbackMock).onSuccess(eq(filledRepos));
    }

    @Test
    public void shouldGetReposFromCloudDataSource() {
        doReturn(emptyRepos).when(reposLocalDataSource).getRepos(FIRST_OFFSET_BLOCK);
        doReturn(filledRepos).when(reposCloudDataSource).getRepos(FIRST_OFFSET_BLOCK, DATA_BLOCK_SIZE);

        reposRepository.getRepos(reposCallbackMock);

        verify(reposLocalDataSource).getRepos(eq(FIRST_OFFSET_BLOCK));
        verify(reposCloudDataSource).getRepos(eq(FIRST_OFFSET_BLOCK), eq(DATA_BLOCK_SIZE));
        verify(reposCallbackMock).onSuccess(eq(filledRepos));
    }

    @Test
    public void shouldInvalidateLocalDataSource() {
        reposRepository.invalidateData();

        verify(reposLocalDataSource).invalidate();
    }
}
