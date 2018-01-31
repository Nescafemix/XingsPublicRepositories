package com.joanfuentes.xingsprojectrepositories.data.api.endpoint.model;

public class RepoEndpointResponse {
    public String name;
    public String description;
    public RepoOwnerEndpointResponse owner;
    public String html_url;
    public boolean fork;
}
