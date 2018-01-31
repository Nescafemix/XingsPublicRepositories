package com.joanfuentes.xingsprojectrepositories.data.datasource;

import com.joanfuentes.xingsprojectrepositories.UnitTest;
import com.joanfuentes.xingsprojectrepositories.data.api.RepoApi;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class ReposCloudDataSourceUnitTest extends UnitTest{
    private ReposCloudDataSource datasource;
    @Mock RepoApi repoApiMock;

    @Override
    protected void onSetup() {
        datasource = new ReposCloudDataSource(repoApiMock);
    }

    @Test
    public void shouldGetRepos() {
        datasource.getRepos();

        verify(repoApiMock).getRepos();
    }
}
