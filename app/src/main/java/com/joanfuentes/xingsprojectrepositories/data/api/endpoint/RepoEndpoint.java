package com.joanfuentes.xingsprojectrepositories.data.api.endpoint;

import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.model.ReposEndpointResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RepoEndpoint {
    String ENDPOINT = "/users/{user}/repos";

    @GET(ENDPOINT)
    Call<ReposEndpointResponse> getRepos(@Path("user") String user,
                                         @Query("page") int page,
                                         @Query("per_page") int perPage);
}