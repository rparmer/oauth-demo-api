package com.rparmer.sample.security.github;

import org.springframework.security.web.DefaultRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GithubRedirectStrategy extends DefaultRedirectStrategy {

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response,
                             String url) throws IOException {
        String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
        redirectUrl = response.encodeRedirectURL(redirectUrl);
        String tokenType = response.getHeader("X-Token-Type");
        String accessToken = response.getHeader("X-Access-Token");
        String redirectUrlWithParams = String.format("%s?token_type=%s&access_token=%s", redirectUrl, tokenType, accessToken);
        response.sendRedirect(redirectUrlWithParams);
    }
}
