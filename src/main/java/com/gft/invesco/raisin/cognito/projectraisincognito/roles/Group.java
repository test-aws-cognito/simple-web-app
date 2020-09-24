package com.gft.invesco.raisin.cognito.projectraisincognito.roles;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table( name = "TBL_GROUPS" )
public class Group {

    @Id
    private Long id;

    private String name;

    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable(
        name = "TBL_GROUPS_ROLES",
        joinColumns = @JoinColumn( name = "group_id" ),
        inverseJoinColumns = @JoinColumn( name = "role_id" ) )
    Set< Role > roles;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Set< Role > getRoles() {
        return roles;
    }

    public void setRoles( Set< Role > roles ) {
        this.roles = roles;
    }
}
