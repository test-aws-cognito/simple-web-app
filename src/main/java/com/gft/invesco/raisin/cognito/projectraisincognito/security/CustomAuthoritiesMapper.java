package com.gft.invesco.raisin.cognito.projectraisincognito.security;

import com.gft.invesco.raisin.cognito.projectraisincognito.roles.Group;
import com.gft.invesco.raisin.cognito.projectraisincognito.roles.GroupsRepository;
import com.gft.invesco.raisin.cognito.projectraisincognito.roles.Role;

import net.minidev.json.JSONArray;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CustomAuthoritiesMapper implements GrantedAuthoritiesMapper {

    private final GroupsRepository groupsRepository;

    public CustomAuthoritiesMapper( GroupsRepository groupsRepository ) {
        this.groupsRepository = groupsRepository;
    }

    @Override
    public Collection< ? extends GrantedAuthority > mapAuthorities(
        Collection< ? extends GrantedAuthority > authorities
    ) {
        Set< GrantedAuthority > mappedAuthorities = new HashSet<>();

        Map< String, Group > groupsData = findAllGroups();

        authorities
            .stream()
            .filter( authority -> authority instanceof OidcUserAuthority )
            .map( authority -> ( OidcUserAuthority ) authority )
            .flatMap( this::fidnCognitoGroups )
            .flatMap( group -> mapGroupToAuthority( groupsData, group.getAuthority() ) )
            .forEach( mappedAuthorities::add );

        return mappedAuthorities;
    }

    private Stream< GrantedAuthority > mapGroupToAuthority( Map< String, Group > groupsData, String groupName ) {
        if ( !groupsData.containsKey( groupName ) ) {
            return Stream.empty();
        }

        return Stream
            .of( groupsData.get( groupName ) )
            .flatMap( group -> group.getRoles().stream() )
            .map( Role::getName )
            .map( SimpleGrantedAuthority::new );
    }

    private Map< String, Group > findAllGroups() {
        return groupsRepository
            .findAll()
            .stream()
            .collect( Collectors.toMap( Group::getName, group -> group ) );
    }

    private Stream< GrantedAuthority > fidnCognitoGroups( OidcUserAuthority oidcUserAuthority ) {
        return Optional.ofNullable(
            oidcUserAuthority
                .getIdToken()
                .getClaim( "cognito:groups" ) )
                       .filter( claims -> claims instanceof JSONArray )
                       .map( claims -> ( JSONArray ) claims )
                       .map( Collection::stream )
                       .orElse( Stream.empty() )
                       .map( claim -> new SimpleGrantedAuthority( claim.toString() ) );
    }
}
