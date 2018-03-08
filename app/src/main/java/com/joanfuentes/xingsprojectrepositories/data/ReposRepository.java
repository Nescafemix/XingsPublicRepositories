package com.joanfuentes.xingsprojectrepositories.data;

import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposCloudDataSource;
import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposLocalDataSource;
import com.joanfuentes.xingsprojectrepositories.domain.model.Repo;

import java.util.List;

import javax.inject.Inject;

public class ReposRepository extends BaseOffsetRepository {
    private final ReposCloudDataSource cloudDataSource;
    private final ReposLocalDataSource localDataSource;
    private int offsetBlock;
    private int dataBlockSize;

    @Inject
    public ReposRepository(ReposLocalDataSource localDataSource,
                           ReposCloudDataSource cloudDataSource) {
        this.localDataSource = localDataSource;
        this.cloudDataSource = cloudDataSource;
        this.offsetBlock = FISRT_OFFSET_BLOCK;
        this.dataBlockSize = DATA_BLOCK_SIZE;
    }

    public void getRepos(final Callback callback) {
        List<Repo> repos = localDataSource.getRepos(offsetBlock);
        if (repos != null && !repos.isEmpty()) {
            offsetBlock++;
            callback.onSuccess(repos);
        } else {
            try {
                repos = cloudDataSource.getRepos(offsetBlock, dataBlockSize);
                localDataSource.saveRepos(repos, offsetBlock);
                offsetBlock++;
                callback.onSuccess(repos);
            } catch (Exception error) {
                callback.onError();
            }
        }
    }

    public void invalidateCaches() {
        offsetBlock = FISRT_OFFSET_BLOCK;
        localDataSource.invalidate();
    }

    public interface Callback {
        void onSuccess(final List<Repo> repos);
        void onError();
    }
}
