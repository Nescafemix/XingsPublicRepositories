package com.joanfuentes.xingsprojectrepositories.data.api;

abstract class AbsRetrofitApi {
    static final int ITEMS_PER_PAGE = 10;
    static final String XING_USER = "xing";
    static final String PERSONAL_ACCESS_TOKEN = "<PUT-HERE-YOUR-ACCESS-TOKEN>";

    String getAccessToken(String accessToken) {
        return ("<PUT-HERE-YOUR-ACCESS-TOKEN>".equalsIgnoreCase(accessToken) ? null : accessToken);
    }
}
