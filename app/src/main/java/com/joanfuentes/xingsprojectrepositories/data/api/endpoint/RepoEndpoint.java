package com.joanfuentes.xingsprojectrepositories.data.api.endpoint;

import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.model.RepoEndpointResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RepoEndpoint {
    String ENDPOINT = "/users/{user}/repos";

    @GET(ENDPOINT)
    Call<List<RepoEndpointResponse>> getRepos(@Path("user") String user,
                                              @Query("page") int page,
                                              @Query("per_page") int perPage);
}