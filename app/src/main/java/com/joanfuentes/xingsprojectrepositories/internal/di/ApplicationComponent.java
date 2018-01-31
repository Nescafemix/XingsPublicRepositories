package com.joanfuentes.xingsprojectrepositories.internal.di;

import android.app.Application;

import com.joanfuentes.xingsprojectrepositories.threading.PostExecutionThread;
import com.joanfuentes.xingsprojectrepositories.threading.ThreadExecutor;

public interface ApplicationComponent {
    Application getApplication();
    ThreadExecutor getThreadExecutor();
    PostExecutionThread getPostExecutionThread();
}