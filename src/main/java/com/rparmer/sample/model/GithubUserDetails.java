package com.rparmer.sample.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GithubUserDetails {

    @SerializedName("login")
    private String login;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("type")
    private String type;

    @SerializedName("company")
    private String company;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("organizations_url")
    private String organizationsUrl;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getOrganizationsUrl() {
        return organizationsUrl;
    }

    public void setOrganizationsUrl(String organizationsUrl) {
        this.organizationsUrl = organizationsUrl;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
