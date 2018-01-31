package com.joanfuentes.xingsprojectrepositories.data;

import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposCloudDataSource;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.List;

import javax.inject.Inject;

public class ReposRepository {
    private final ReposCloudDataSource cloudDataSource;

    @Inject
    public ReposRepository(ReposCloudDataSource cloudDataSource) {
        this.cloudDataSource = cloudDataSource;
    }

    public void getRepos(final int page, final Callback callback) {
        try {
            callback.onSuccess(cloudDataSource.getRepos(page));
        } catch (Exception error) {
            callback.onError();
        }
    }

    public interface Callback {
        void onSuccess(final List<Repo> repos);
        void onError();
    }
}
