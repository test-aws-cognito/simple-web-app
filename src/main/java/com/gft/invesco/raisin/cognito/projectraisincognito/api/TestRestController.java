package com.gft.invesco.raisin.cognito.projectraisincognito.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

@RestController
@RequestMapping( "/api/test" )
public class TestRestController {

    @PreAuthorize( "hasAuthority('USER')" )
    @GetMapping( "/ping_user" )
    public String pingUser() {
        return "Message for USER";
    }

    @PreAuthorize( "hasAuthority('ADMIN')" )
    @GetMapping( "/ping_admin" )
    public String pingAdmin() {
        return "Message for ADMIN";
    }


    @GetMapping( "/ping" )
    public String ping() {
        return MessageFormat.format(
            "Pong from {0} ({1})! <br /><br /> User roles: <br /> {2} <br /><br /> OAuth user data: <br /> {3}",
            getHostIp(),
            getHostname(),
            getRoles(),
            getUserData() );
    }

    private String getRoles() {
        return SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getAuthorities()
            .toString();
    }

    private String getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( authentication == null ) {
            return "User is not logged in :(";
        }

        if ( authentication.getPrincipal() instanceof DefaultOidcUser ) {
            String userData = ( ( DefaultOidcUser ) authentication.getPrincipal() ).getAttributes().toString();

            return userData
                       .replaceAll( "[{]", "{ <br />&nbsp;&nbsp;" )
                       .replaceAll( ", ", ", <br />&nbsp;&nbsp;" )
                       .replaceAll( "}", "<br />}" )
                   + "<br /><br />";
        } else {
            return authentication.getPrincipal().toString() + "<br /><br />";
        }
    }

    private String getHostname() {
        String hostname = System.getenv( "HOSTNAME" );
        return StringUtils.isEmpty( hostname ) ? "HOSTNAME unknown" : hostname;
    }

    private String getHostIp() {
        String ip = "(unknown";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch ( UnknownHostException e ) {
            e.printStackTrace();
        }

        return ip;
    }
}
