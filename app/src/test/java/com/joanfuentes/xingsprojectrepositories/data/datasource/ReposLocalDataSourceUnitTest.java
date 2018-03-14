package com.joanfuentes.xingsprojectrepositories.data.datasource;

import com.joanfuentes.xingsprojectrepositories.UnitTest;
import com.joanfuentes.xingsprojectrepositories.data.memcache.RepoMemcache;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class ReposLocalDataSourceUnitTest extends UnitTest{
    private static final int FIRST_OFFSET_BLOCK = 1;
    private ReposLocalDataSource datasource;
    @Mock private RepoMemcache repoMemcacheMock;
    @Mock private List<Repo> reposMock;

    @Override
    protected void onSetup() {
        datasource = new ReposLocalDataSource(repoMemcacheMock);
    }

    @Test
    public void shouldGetRepos() {
        datasource.getRepos(FIRST_OFFSET_BLOCK);

        verify(repoMemcacheMock).getRepos(eq(FIRST_OFFSET_BLOCK));
    }

    @Test
    public void shouldSaveRepos() {
        datasource.saveRepos(reposMock, FIRST_OFFSET_BLOCK);

        verify(repoMemcacheMock).saveRepos(eq(reposMock), eq(FIRST_OFFSET_BLOCK));
    }
}
