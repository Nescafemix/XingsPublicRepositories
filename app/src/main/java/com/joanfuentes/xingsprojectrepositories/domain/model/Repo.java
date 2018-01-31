package com.joanfuentes.xingsprojectrepositories.domain.model;

import java.io.Serializable;

public class Repo implements Serializable {
    private String name;
    private String description;
    private String ownerLogin;
    private String ownerHtmlUrl;
    private String htmlUrl;
    private boolean fork;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public String getOwnerHtmlUrl() {
        return ownerHtmlUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public boolean isFork() {
        return fork;
    }

    public static class Builder {
        private String name;
        private String description;
        private String ownerLogin;
        private String ownerHtmlUrl;
        private String htmlUrl;
        private boolean fork;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setOwnerLogin(String ownerLogin) {
            this.ownerLogin = ownerLogin;
            return this;
        }

        public Builder setOwnerHtmlUrl(String ownerHtmlUrl) {
            this.ownerHtmlUrl = ownerHtmlUrl;
            return this;
        }

        public Builder setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
            return this;
        }

        public Builder setFork(boolean fork) {
            this.fork = fork;
            return this;
        }

        public Repo build() {
            Repo repo = new Repo();
            repo.name = name;
            repo.description = description;
            repo.ownerLogin = ownerLogin;
            repo.ownerHtmlUrl = ownerHtmlUrl;
            repo.htmlUrl = htmlUrl;
            repo.fork = fork;
            return repo;
        }
    }
}
