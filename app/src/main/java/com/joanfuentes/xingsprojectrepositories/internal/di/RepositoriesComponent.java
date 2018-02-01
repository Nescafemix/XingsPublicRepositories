package com.joanfuentes.xingsprojectrepositories.internal.di;

import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposCloudDataSource;
import com.joanfuentes.xingsprojectrepositories.data.datasource.ReposLocalDataSource;

interface RepositoriesComponent {
    ReposLocalDataSource getReposLocalDataSource();
    ReposCloudDataSource getReposCloudDataSource();
}
