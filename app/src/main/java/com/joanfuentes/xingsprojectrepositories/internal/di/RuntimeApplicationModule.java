package com.joanfuentes.xingsprojectrepositories.internal.di;

import com.joanfuentes.xingsprojectrepositories.Application;
import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.internal.di.EndpointsModule;
import com.joanfuentes.xingsprojectrepositories.threading.JobExecutor;
import com.joanfuentes.xingsprojectrepositories.threading.PostExecutionThread;
import com.joanfuentes.xingsprojectrepositories.threading.ThreadExecutor;
import com.joanfuentes.xingsprojectrepositories.threading.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module( includes = {EndpointsModule.class})
public class RuntimeApplicationModule implements ApplicationModule {
    private final Application application;

    public RuntimeApplicationModule(Application application) {
        this.application = application;
    }

    @Override @Provides
    public Application provideApplication() {
        return this.application;
    }

    @Override @Provides @Singleton
    public ThreadExecutor provideThreadExecutor() {
        return JobExecutor.getInstance();
    }

    @Override @Provides @Singleton
    public PostExecutionThread providePostExecutionThread() {
        return UIThread.getInstance();
    }
}