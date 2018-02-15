package com.joanfuentes.xingsprojectrepositories.data.datasource.internal.di;

import com.joanfuentes.xingsprojectrepositories.data.memcache.RepoMemcache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class RepositoriesModule {
    @Provides @Singleton
    RepoMemcache getRepoMemCache() {
        return RepoMemcache.getInstance();
    }
}
