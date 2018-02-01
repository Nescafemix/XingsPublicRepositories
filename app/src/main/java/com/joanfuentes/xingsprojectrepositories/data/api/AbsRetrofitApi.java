package com.joanfuentes.xingsprojectrepositories.data.api;

abstract public class AbsRetrofitApi {
    protected static final String XING_USER = "xing";
    protected static final String PERSONAL_ACCESS_TOKEN = "<PUT-HERE-YOUR-ACCESS-TOKEN>";

    protected String getAccessToken(String accessToken) {
        if (PERSONAL_ACCESS_TOKEN.equalsIgnoreCase("<PUT-HERE-YOUR-ACCESS-TOKEN>")) {
            accessToken = null;
        }
        return accessToken;
    }
}
