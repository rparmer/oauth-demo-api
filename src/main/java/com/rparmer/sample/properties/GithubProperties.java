package com.rparmer.sample.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "github")
public class GithubProperties {

    private Resource resource = new Resource();

    public static class Resource {
        private String userInfoUri;
        private String userOrgsUri;

        public String getUserInfoUri() {
            return userInfoUri;
        }

        public void setUserInfoUri(String userInfoUri) {
            this.userInfoUri = userInfoUri;
        }

        public String getUserOrgsUri() {
            return userOrgsUri;
        }

        public void setUserOrgsUri(String userOrgsUri) {
            this.userOrgsUri = userOrgsUri;
        }
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
