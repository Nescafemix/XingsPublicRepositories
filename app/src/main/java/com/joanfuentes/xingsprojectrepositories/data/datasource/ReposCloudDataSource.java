package com.joanfuentes.xingsprojectrepositories.data.datasource;

import com.joanfuentes.xingsprojectrepositories.data.api.RepoApi;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.List;

import javax.inject.Inject;

public class ReposCloudDataSource {
    private final RepoApi api;

    @Inject
    public ReposCloudDataSource(RepoApi api) {
        this.api = api;
    }

    public List<Repo> getRepos() {
        return api.getRepos();
    }
}
