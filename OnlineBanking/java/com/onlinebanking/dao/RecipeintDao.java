package com.onlinebanking.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.onlinebanking.domain.details.Recipient;

public interface RecipeintDao extends CrudRepository<Recipient, Long> {

	List<Recipient> findAll();

	Recipient findByName(String recipientName);

	void deleteByName(String recipientName);

}
