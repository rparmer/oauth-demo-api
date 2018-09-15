package com.rparmer.sample.controller;

import com.rparmer.sample.model.GithubUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class GreetingController {

    @GetMapping("/hello")
    public String getNamedGreeting(Authentication user) {
        return String.format("Hello %s", ((GithubUser) user.getDetails()).getName());
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
