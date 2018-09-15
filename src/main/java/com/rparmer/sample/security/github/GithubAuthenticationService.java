package com.rparmer.sample.security.github;

import com.google.gson.Gson;
import com.rparmer.sample.model.GithubUserDetails;
import com.rparmer.sample.properties.GithubProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
public class GithubAuthenticationService {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    private GithubProperties githubProperties;

    public Authentication getAuthentication(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) return null;

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, authorization);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(githubProperties.getResource().getUserInfoUri(), HttpMethod.GET, entity, String.class);

        GithubUserDetails details = gson.fromJson(response.getBody(), GithubUserDetails.class);

        if (null == details.getLogin()) return null;

        PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(details.getLogin(), null, null);
        return authToken;
    }
}
