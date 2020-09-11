package com.gft.invesco.raisin.cognito.projectraisincognito;

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

    @GetMapping( "/ping" )
    public String ping() {

        return MessageFormat.format(
            "Pong from {0} ({1})! <br /> User data: <br /> {2}",
            getHostIp(),
            getHostname(),
            getUserData() );
    }

    private String getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( authentication == null ) {
            return "User is not logged in :(";
        }

        String userData = ( ( DefaultOidcUser ) authentication.getPrincipal() ).getAttributes().toString();

        return userData
            .replaceAll( "[{]", "{ <br />&nbsp;&nbsp;" )
            .replaceAll( ", ", ", <br />&nbsp;&nbsp;" )
            .replaceAll( "}", "<br />}" );
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
