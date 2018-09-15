package com.rparmer.sample.security.github;

import com.rparmer.sample.model.GithubCredentials;
import com.rparmer.sample.model.GithubOrganization;
import com.rparmer.sample.model.GithubUser;
import com.rparmer.sample.properties.GithubProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;

@Component
public class GithubAuthenticationService {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GithubProperties githubProperties;

    public Authentication getAuthentication(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) return null;

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.add(AUTHORIZATION, authorization);
        HttpEntity<String> authEntity = new HttpEntity<>(authHeaders);

        ResponseEntity<GithubUser> response = restTemplate.exchange(githubProperties.getResource().getUserInfoUri(),
                HttpMethod.GET, authEntity, GithubUser.class);
        GithubUser details = response.getBody();

        if (null == details.getUsername()) return null;

        String[] authParts = StringUtils.split(authorization, " ");
        GithubCredentials credentials = new GithubCredentials(authParts[0], authParts[1]);

        Collection<? extends GrantedAuthority> authorities = getAuthorities(getOrganizations(authEntity));

        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(details.getUsername(), credentials, authorities);
        authentication.setDetails(details);

        return authentication;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<GithubOrganization> orgs) {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        orgs.forEach(org -> {
            authorities.add(new SimpleGrantedAuthority(org.getName()));
        });
        return authorities;
    }

    private Collection<GithubOrganization> getOrganizations(HttpEntity authEntity) {
        ResponseEntity<Collection<GithubOrganization>> response = restTemplate.exchange(githubProperties.getResource().getUserOrgsUri(),
                HttpMethod.GET, authEntity, new ParameterizedTypeReference<Collection<GithubOrganization>>(){});
        return response.getBody();
    }
}
