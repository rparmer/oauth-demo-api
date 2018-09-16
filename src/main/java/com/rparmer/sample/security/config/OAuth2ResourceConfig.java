package com.rparmer.sample.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

//@Configuration
//@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

	private TokenExtractor tokenExtractor = new BearerTokenExtractor();
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.addFilterAfter(new OncePerRequestFilter() {
//			@Override
//			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//					FilterChain filterChain) throws ServletException, IOException {
//				System.out.println("filter called");
//				// We don't want to allow access to a resource with no token so clear
//				// the security context in case it is actually an OAuth2Authentication
//				if (tokenExtractor.extract(request) == null) {
//					SecurityContextHolder.clearContext();
//				}
//				filterChain.doFilter(request, response);
//			}
//		}, AbstractPreAuthenticatedProcessingFilter.class);
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/user").permitAll().anyRequest().authenticated();
	}

	@Bean
	public AccessTokenConverter accessTokenConverter() {
		return new DefaultAccessTokenConverter();
	}

	@Bean
	public RemoteTokenServices remoteTokenServices() {
		final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
		remoteTokenServices.setCheckTokenEndpointUrl("https://github.com/login/oauth/access_token");
		remoteTokenServices.setClientId("7e110ff6371f99282d05");
		remoteTokenServices.setClientSecret("c5f14b040ef65364d5ddb7d3b5631bcc705b59cf");
		remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
		return remoteTokenServices;
	}
}
