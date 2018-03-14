package com.joanfuentes.xingsprojectrepositories.data.memcache;

import com.joanfuentes.xingsprojectrepositories.UnitTest;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RepoMemcacheUnitTest extends UnitTest {
    private static final int FIRST_OFFSET_BLOCK = 1;
    private RepoMemcache repoMemcache;
    private List<Repo> emptyRepos;
    private List<Repo> filledRepos;
    @Mock private Repo repoMock;

    @Override
    protected void onSetup() {
        repoMemcache = new RepoMemcache();
        emptyRepos = new ArrayList<>();
        filledRepos = new ArrayList<>();
        filledRepos.add(repoMock);
    }

    @Test
    public void shouldBeInvalidedTheCache() {
        repoMemcache.saveRepos(filledRepos, FIRST_OFFSET_BLOCK);
        repoMemcache.invalidate();

        assertEquals(emptyRepos, repoMemcache.getRepos(FIRST_OFFSET_BLOCK));
    }

    @Test
    public void shouldGetReposFromValidCache() {
        repoMemcache.saveRepos(filledRepos, FIRST_OFFSET_BLOCK);

        assertEquals(filledRepos, repoMemcache.getRepos(FIRST_OFFSET_BLOCK));
    }

    @Test
    public void shouldNotGetReposFromValidCache() {
        assertEquals(emptyRepos, repoMemcache.getRepos(FIRST_OFFSET_BLOCK));
    }
}
