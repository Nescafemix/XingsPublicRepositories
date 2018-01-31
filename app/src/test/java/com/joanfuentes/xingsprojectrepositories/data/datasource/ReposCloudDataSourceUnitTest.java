package com.joanfuentes.xingsprojectrepositories.data.datasource;

import com.joanfuentes.xingsprojectrepositories.UnitTest;
import com.joanfuentes.xingsprojectrepositories.data.api.RepoApi;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class ReposCloudDataSourceUnitTest extends UnitTest{
    private ReposCloudDataSource datasource;
    private int FIRST_PAGE = 1;
    @Mock RepoApi repoApiMock;

    @Override
    protected void onSetup() {
        datasource = new ReposCloudDataSource(repoApiMock);
    }

    @Test
    public void shouldGetRepos() {
        datasource.getRepos(FIRST_PAGE);

        verify(repoApiMock).getRepos(eq(FIRST_PAGE));
    }
}