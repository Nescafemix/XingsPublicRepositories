package com.joanfuentes.xingsprojectrepositories.data.api;

import com.joanfuentes.xingsprojectrepositories.UnitTest;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AbsRetrofitUnitTest extends UnitTest {
    private static final String SOME_CONFIGURED_ACCESS_TOKEN = "access-token";
    private static final String SOME_NOT_CONFIGURED_ACCESS_TOKEN = "<PUT-HERE-YOUR-ACCESS-TOKEN>";

    @Override
    protected void onSetup() {}

    @Test
    public void shouldHaveAccessTokenConfigured() {
        AbsRetrofitApi absRetrofitApi = new AbsRetrofitApi() {};

        boolean isAccessTokenConfigured =
                absRetrofitApi.getAccessToken(SOME_CONFIGURED_ACCESS_TOKEN) != null;

        assertTrue(isAccessTokenConfigured);
    }

    @Test
    public void shouldHaveAccessTokenNotConfigured() {
        AbsRetrofitApi absRetrofitApi = new AbsRetrofitApi() {};

        boolean isAccessTokenConfigured =
                absRetrofitApi.getAccessToken(SOME_NOT_CONFIGURED_ACCESS_TOKEN) != null;

        assertFalse(isAccessTokenConfigured);
    }
}
