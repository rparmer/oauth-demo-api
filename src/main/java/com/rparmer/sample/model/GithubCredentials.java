package com.rparmer.sample.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GithubCredentials {

    private String type;
    private String token;

    public GithubCredentials() {}

    public GithubCredentials(String type, String token) {
        this.type = type;
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
