package com.api.marvel.persistence.repository;

import com.api.marvel.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r WHERE r.roleName IN :roleNames")
    List<RoleEntity> findRoles(List<String> roleNames);

    List<RoleEntity> findRoleEntitiesByRoleNameIn(List<String> roleNames);
}
