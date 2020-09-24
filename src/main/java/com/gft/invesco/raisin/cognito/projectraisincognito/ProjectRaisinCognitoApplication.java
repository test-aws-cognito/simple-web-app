package com.gft.invesco.raisin.cognito.projectraisincognito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaRepositories( "com.gft.invesco.raisin.cognito.projectraisincognito" )
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class ProjectRaisinCognitoApplication {

    public static void main( String[] args ) {
        SpringApplication.run( ProjectRaisinCognitoApplication.class, args );
    }

}
