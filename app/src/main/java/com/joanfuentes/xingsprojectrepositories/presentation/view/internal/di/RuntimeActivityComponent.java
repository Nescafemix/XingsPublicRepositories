package com.joanfuentes.xingsprojectrepositories.presentation.view.internal.di;

import com.joanfuentes.xingsprojectrepositories.internal.di.ApplicationComponent;
import com.joanfuentes.xingsprojectrepositories.internal.di.scopes.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = RuntimeActivityModule.class)
public interface RuntimeActivityComponent extends ActivityComponent { }