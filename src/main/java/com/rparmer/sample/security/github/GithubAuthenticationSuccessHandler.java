package com.rparmer.sample.security.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rparmer.sample.model.GithubCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GithubAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        GithubCredentials credentials = new GithubCredentials(details.getTokenType(), details.getTokenValue());
        response.getWriter().write(mapper.writeValueAsString(credentials));
        response.getWriter().flush();
        response.getWriter().close();
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
