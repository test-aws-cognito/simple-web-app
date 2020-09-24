package com.gft.invesco.raisin.cognito.projectraisincognito.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository< Group, Long > {

}
