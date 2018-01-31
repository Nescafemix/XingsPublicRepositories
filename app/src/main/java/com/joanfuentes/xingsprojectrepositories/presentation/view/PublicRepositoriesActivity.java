package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.os.Bundle;

import com.joanfuentes.xingsprojectrepositories.Application;
import com.joanfuentes.xingsprojectrepositories.R;
import com.joanfuentes.xingsprojectrepositories.presentation.view.internal.di.DaggerRuntimeActivityComponent;
import com.joanfuentes.xingsprojectrepositories.presentation.view.internal.di.RuntimeActivityModule;

public class PublicRepositoriesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    int onRequestLayout() {
        return R.layout.activity_public_repositories;
    }

    @Override
    void onInitializeInjection() {
        DaggerRuntimeActivityComponent
                .builder()
                .applicationComponent(((Application)getApplication()).getApplicationComponent())
                .runtimeActivityModule(new RuntimeActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    void onViewReady() {}
}
