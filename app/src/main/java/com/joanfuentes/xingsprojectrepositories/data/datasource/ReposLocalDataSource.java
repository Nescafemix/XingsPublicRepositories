package com.joanfuentes.xingsprojectrepositories.data.datasource;

import com.joanfuentes.xingsprojectrepositories.data.memcache.RepoMemcache;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.List;

import javax.inject.Inject;

public class ReposLocalDataSource {
    private final RepoMemcache repoMemcache;

    @Inject
    public ReposLocalDataSource(RepoMemcache repoMemcache) {
        this.repoMemcache = repoMemcache;
    }

    public List<Repo> getRepos(int page) {
        return repoMemcache.getRepos(page);
    }

    public void saveRepos(List<Repo> repos, int page) {
        repoMemcache.saveRepos(repos, page);
    }

    public void invalidate() {
        repoMemcache.invalidate();
    }
}
