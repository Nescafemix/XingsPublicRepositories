package com.joanfuentes.xingsprojectrepositories.data.api.endpoint.internal.di;

import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.RepoEndpoint;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module
public class EndpointsModule {
    private static final String BASE_URL = "https://api.github.com/";

    @Provides
    RepoEndpoint provideRepoEndpoint() {
        return getRetrofitBuilder().create(RepoEndpoint.class);
    }

    private Retrofit getRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}