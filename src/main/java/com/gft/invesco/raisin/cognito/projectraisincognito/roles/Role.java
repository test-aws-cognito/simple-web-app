package com.gft.invesco.raisin.cognito.projectraisincognito.roles;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table( name = "TBL_ROLES" )
public class Role {

    @Id
    private Long id;

    private String name;

    @ManyToMany( mappedBy = "roles" )
    private Set< Group > groups;

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

    public Set< Group > getGroups() {
        return groups;
    }

    public void setGroups( Set< Group > groups ) {
        this.groups = groups;
    }
}
