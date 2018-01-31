package com.joanfuentes.xingsprojectrepositories.data.api.model;

import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.model.RepoEndpointResponse;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RepoResponseMapper {

    @Inject
    public RepoResponseMapper(){}

    public List<Repo> map(List<RepoEndpointResponse> response) {
        List<Repo> repos = new ArrayList<>();
        for (RepoEndpointResponse result: response) {
            Repo repo = new Repo.Builder()
                    .setName(result.name)
                    .setDescription(result.description)
                    .setHtmlUrl(result.html_url)
                    .setFork(result.fork)
                    .setOwnerLogin(result.owner.login)
                    .setOwnerHtmlUrl(result.owner.html_url)
                    .build();
            repos.add(repo);
        }
        return repos;
    }
}
