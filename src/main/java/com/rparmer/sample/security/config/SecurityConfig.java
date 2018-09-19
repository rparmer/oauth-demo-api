package com.rparmer.sample.security.config;

import com.rparmer.sample.properties.AuthProperties;
import com.rparmer.sample.security.github.GithubAuthenticationFilter;
import com.rparmer.sample.security.github.GithubAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    public class ApiConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthProperties authProperties;

        @Autowired
        private GithubAuthenticationFilter githubAuthenticationFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/greeting/unsecured").permitAll()
                .antMatchers("/greeting/user").hasAuthority(authProperties.getGithubAdminOrg())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(githubAuthenticationFilter, BasicAuthenticationFilter.class);
        }
    }

    @Configuration
    @EnableOAuth2Client
    @Order(1)
    public class LoginConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private OAuth2ClientContext oauth2ClientContext;

        @Autowired
        private GithubAuthenticationSuccessHandler githubAuthenticationSuccessHandler;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .antMatcher("/login/**")
                .authorizeRequests()
                .antMatchers("/login**").permitAll()
                .and().addFilterBefore(githubFilter(), BasicAuthenticationFilter.class);
        }

        private Filter githubFilter() {
            OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
            OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
            githubFilter.setRestTemplate(githubTemplate);
            UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId());
            tokenServices.setRestTemplate(githubTemplate);
            githubFilter.setTokenServices(tokenServices);
            githubFilter.setAuthenticationSuccessHandler(githubAuthenticationSuccessHandler);
            return githubFilter;
        }

        @Bean
        public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
            FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
            registration.setFilter(filter);
            registration.setOrder(-100);
            return registration;
        }

        @Bean
        @ConfigurationProperties("github.client")
        public AuthorizationCodeResourceDetails github() {
            return new AuthorizationCodeResourceDetails();
        }

        @Bean
        @ConfigurationProperties("github.resource")
        public ResourceServerProperties githubResource() {
            return new ResourceServerProperties();
        }
    }
}
