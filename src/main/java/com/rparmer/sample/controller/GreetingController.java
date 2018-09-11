package com.rparmer.sample.controller;

import com.google.gson.Gson;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class GreetingController {

    @GetMapping("/hello")
    public String getNamedGreeting(Principal user) {
        return String.format("Hello %s", user.getName());
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/user/details")
    public String userDetails(OAuth2Authentication authentication) {
        Map<String, String> details = (Map<String, String>) authentication.getUserAuthentication().getDetails();
        return new Gson().toJson(details);
    }

}
