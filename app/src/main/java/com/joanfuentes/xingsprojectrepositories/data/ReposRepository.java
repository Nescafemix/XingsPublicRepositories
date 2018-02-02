package com.joanfuentes.xingsprojectrepositories.data;

import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposCloudDataSource;
import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposLocalDataSource;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.List;

import javax.inject.Inject;

public class ReposRepository {
    private final ReposCloudDataSource cloudDataSource;
    private final ReposLocalDataSource localDataSource;

    @Inject
    public ReposRepository(ReposLocalDataSource localDataSource,
                           ReposCloudDataSource cloudDataSource) {
        this.localDataSource = localDataSource;
        this.cloudDataSource = cloudDataSource;
    }

    public void getRepos(final int page, final Callback callback) {
        List<Repo> repos = localDataSource.getRepos(page);
        if (repos != null && !repos.isEmpty()) {
            callback.onSuccess(repos);
        } else {
            try {
                repos = cloudDataSource.getRepos(page);
                localDataSource.saveRepos(repos, page);
                callback.onSuccess(repos);
            } catch (Exception error) {
                callback.onError();
            }
        }
    }

    public void invalidateCaches() {
        localDataSource.invalidate();
    }

    public interface Callback {
        void onSuccess(final List<Repo> repos);
        void onError();
    }
}
