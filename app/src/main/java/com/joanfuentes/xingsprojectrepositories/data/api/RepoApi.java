package com.joanfuentes.xingsprojectrepositories.data.api;

import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.RepoEndpoint;
import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.model.RepoEndpointResponse;
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

    public List<Repo> getRepos(int page) {
        List<Repo> result;
        int perPage = 10;
        try {
            final Call<List<RepoEndpointResponse>> apiCaller = endpoint
                    .getRepos(XING_USER, page, perPage);
            final Response<List<RepoEndpointResponse>> response = apiCaller.execute();
            if (response.isSuccessful()) {
                final List<RepoEndpointResponse> reposEndpointResponse = response.body();
                result = mapper.map(reposEndpointResponse);
            } else {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
