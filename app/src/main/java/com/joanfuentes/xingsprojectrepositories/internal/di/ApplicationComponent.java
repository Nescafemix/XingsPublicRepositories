package com.joanfuentes.xingsprojectrepositories.internal.di;

import com.joanfuentes.xingsprojectrepositories.Application;
import com.joanfuentes.xingsprojectrepositories.threading.PostExecutionThread;
import com.joanfuentes.xingsprojectrepositories.threading.ThreadExecutor;

public interface ApplicationComponent extends RepositoriesComponent {
    Application getApplication();
    ThreadExecutor getThreadExecutor();
    PostExecutionThread getPostExecutionThread();
}