package com.rparmer.sample.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private String githubAdminOrg;

    public String getGithubAdminOrg() {
        return githubAdminOrg;
    }

    public void setGithubAdminOrg(String githubAdminOrg) {
        this.githubAdminOrg = githubAdminOrg;
    }
}
