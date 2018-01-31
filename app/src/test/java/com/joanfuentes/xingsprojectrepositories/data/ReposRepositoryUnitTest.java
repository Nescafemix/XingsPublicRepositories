package com.joanfuentes.xingsprojectrepositories.data;

import com.joanfuentes.xingsprojectrepositories.UnitTest;
import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposCloudDataSource;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class ReposRepositoryUnitTest extends UnitTest{
    private ReposRepository reposRepository;
    private int FIRST_PAGE = 1;
    @Mock ReposRepository.Callback reposCallbackMock;
    @Mock ReposCloudDataSource reposCloudDataSource;
    @Mock List<Repo> reposMock;
    @Override
    protected void onSetup() {
        reposRepository = new ReposRepository(reposCloudDataSource);
    }

    @Test
    public void shouldGetRepos() {
        doReturn(reposMock).when(reposCloudDataSource).getRepos(FIRST_PAGE);

        reposRepository.getRepos(FIRST_PAGE, reposCallbackMock);

        verify(reposCloudDataSource).getRepos(eq(FIRST_PAGE));
        verify(reposCallbackMock).onSuccess(eq(reposMock));
    }
}
