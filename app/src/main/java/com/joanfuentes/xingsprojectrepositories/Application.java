package com.joanfuentes.xingsprojectrepositories;

import com.joanfuentes.xingsprojectrepositories.internal.di.ApplicationComponent;
import com.joanfuentes.xingsprojectrepositories.internal.di.DaggerRuntimeApplicationComponent;
import com.joanfuentes.xingsprojectrepositories.internal.di.RuntimeApplicationComponent;
import com.joanfuentes.xingsprojectrepositories.internal.di.RuntimeApplicationModule;

public class Application extends android.app.Application {
    private RuntimeApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjection();
    }

    private void initializeInjection() {
        this.applicationComponent = DaggerRuntimeApplicationComponent
                .builder()
                .runtimeApplicationModule(new RuntimeApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}