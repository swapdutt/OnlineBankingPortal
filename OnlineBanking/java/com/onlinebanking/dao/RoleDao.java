package com.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;

import com.onlinebanking.domain.role.Role;

public interface RoleDao extends CrudRepository<Role, Integer> {

	Role findByName(String name);

}
