package com.joanfuentes.xingsprojectrepositories.presentation.view.internal.di;

import android.app.Activity;

import com.joanfuentes.xingsprojectrepositories.data.api.endpoint.internal.di.EndpointsModule;
import com.joanfuentes.xingsprojectrepositories.presentation.view.PublicRepositoriesActivity;

import dagger.Module;
import dagger.Provides;

@Module(includes = {EndpointsModule.class})
public class RuntimeActivityModule implements ActivityModule {
    private Activity activity;

    public RuntimeActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Override @Provides
    public PublicRepositoriesActivity providePublicRepositoriesActivity() {
        return (PublicRepositoriesActivity) activity;
    }
}