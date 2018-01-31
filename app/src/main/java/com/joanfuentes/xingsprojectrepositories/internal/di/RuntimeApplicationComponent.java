package com.joanfuentes.xingsprojectrepositories.internal.di;

import com.joanfuentes.xingsprojectrepositories.Application;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RuntimeApplicationModule.class)
public interface RuntimeApplicationComponent extends ApplicationComponent {
    void inject(Application application);
}