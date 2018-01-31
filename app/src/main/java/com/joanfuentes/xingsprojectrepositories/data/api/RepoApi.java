package com.joanfuentes.xingsprojectrepositories.data.api;

import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.RepoEndpoint;
import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.model.ReposEndpointResponse;
import com.joanfuentes.xingsprojectrepositories.data.api.model.RepoResponseMapper;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class RepoApi extends AbsRetrofitApi {
    private final RepoEndpoint endpoint;
    private final RepoResponseMapper mapper;

    @Inject
    public RepoApi(RepoEndpoint endpoint, RepoResponseMapper mapper) {
        this.endpoint = endpoint;
        this.mapper = mapper;
    }

    public List<Repo> getRepos() {
        List<Repo> result;
        int page = 1;
        int perPage = 10;
        try {
            final Call<ReposEndpointResponse> apiCaller = endpoint
                    .getRepos(XING_USER, page, perPage);
            final Response<ReposEndpointResponse> response = apiCaller.execute();
            if (response.isSuccessful()) {
                final ReposEndpointResponse reposEndpointResponse = response.body();
                result = mapper.map(reposEndpointResponse.repos);
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
