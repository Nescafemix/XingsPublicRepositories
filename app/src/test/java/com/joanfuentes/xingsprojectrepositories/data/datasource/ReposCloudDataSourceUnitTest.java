package com.joanfuentes.xingsprojectrepositories.data.datasource;

import com.joanfuentes.xingsprojectrepositories.UnitTest;
import com.joanfuentes.xingsprojectrepositories.data.api.RepoApi;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class ReposCloudDataSourceUnitTest extends UnitTest{
    private static final int FIRST_PAGE = 1;
    private static final int DATA_BLOCK_SIZE = 10;
    private ReposCloudDataSource datasource;
    @Mock private RepoApi repoApiMock;

    @Override
    protected void onSetup() {
        datasource = new ReposCloudDataSource(repoApiMock);
    }

    @Test
    public void shouldGetRepos() {
        datasource.getRepos(FIRST_PAGE, DATA_BLOCK_SIZE);

        verify(repoApiMock).getRepos(eq(FIRST_PAGE), eq(DATA_BLOCK_SIZE));
    }
}
