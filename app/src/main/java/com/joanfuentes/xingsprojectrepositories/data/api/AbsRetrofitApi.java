package com.joanfuentes.xingsprojectrepositories.data.api;

abstract class AbsRetrofitApi {
    static final String XING_USER = "xing";
    static final String PERSONAL_ACCESS_TOKEN = "<PUT-HERE-YOUR-ACCESS-TOKEN>";

    String getAccessToken(String accessToken) {
        return ("<PUT-HERE-YOUR-ACCESS-TOKEN>".equalsIgnoreCase(accessToken) ? null : accessToken);
    }
}
