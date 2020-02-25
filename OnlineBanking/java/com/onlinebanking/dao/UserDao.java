package com.onlinebanking.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.onlinebanking.domain.details.User;

public interface UserDao extends CrudRepository<User, Long> {

	User findByName(String username);

	User findByEmail(String email);

	List<User> findAll();

}
