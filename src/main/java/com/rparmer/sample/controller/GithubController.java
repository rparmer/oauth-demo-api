package com.rparmer.sample.controller;

import com.rparmer.sample.model.GithubCredentials;
import com.rparmer.sample.security.github.GithubAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/github")
public class GithubController {

    @Autowired
    private GithubAuthenticationService githubAuthenticationService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/create_token")
    public void createToken(HttpServletResponse response) {
        response.setStatus(302);
        response.setHeader("location", githubAuthenticationService.getAuthorizationUrl());
    }

    @GetMapping("/callback")
    public GithubCredentials callback(@RequestParam("code") String code) {
        GithubCredentials credentials = restTemplate.postForObject(githubAuthenticationService.getAccessTokenUrl(code), null, GithubCredentials.class);
        return credentials;

    }
}
