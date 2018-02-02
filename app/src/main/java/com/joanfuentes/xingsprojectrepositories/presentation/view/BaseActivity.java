package com.joanfuentes.xingsprojectrepositories.presentation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onRequestLayout());
        initializeInjection();
        initializeViewsInjection();
        onViewReady();
    }

    private void initializeInjection() {
        onInitializeInjection();
    }

    private void initializeViewsInjection() {
        ButterKnife.bind(this);
    }

    abstract int onRequestLayout();

    abstract void onInitializeInjection();

    abstract void onViewReady();
}
