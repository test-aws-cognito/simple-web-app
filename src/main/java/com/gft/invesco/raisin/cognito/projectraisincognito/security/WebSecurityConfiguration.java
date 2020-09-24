package com.gft.invesco.raisin.cognito.projectraisincognito.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomAuthoritiesMapper customAuthoritiesMapper;

    public WebSecurityConfiguration( CustomAuthoritiesMapper customAuthoritiesMapper ) {
        this.customAuthoritiesMapper = customAuthoritiesMapper;
    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
            // CSRF
            .csrf( csrf -> {
            } )
            // Enable some paths (healthcheck etc)
            .authorizeRequests( auth -> auth
                .antMatchers( "/healthcheck.html" )
                .permitAll() )
            // Any other request needs authentication
            .requiresChannel( channel -> channel
                .anyRequest()
                .requiresSecure() )
            // Authorization
            .authorizeRequests( auth -> auth
                .anyRequest().authenticated() )
            .oauth2Login( oauth -> oauth
                .userInfoEndpoint( userInfo -> userInfo
                    .userAuthoritiesMapper( customAuthoritiesMapper ) ) )
            .logout( logout -> logout
                .logoutSuccessUrl( "/" ) )
        ;
    }
}
