package com.joanfuentes.xingsprojectrepositories;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
@SmallTest
public abstract class UnitTest {
    @Before
    public final void setup() {
        initializeMocks();
        onSetup();
    }

    private void initializeMocks() {
        MockitoAnnotations.initMocks(this);
    }

    protected abstract void onSetup();
}