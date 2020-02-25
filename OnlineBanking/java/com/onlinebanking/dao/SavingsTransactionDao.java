package com.onlinebanking.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.onlinebanking.domain.details.SavingsTransaction;

public interface SavingsTransactionDao extends CrudRepository<SavingsTransaction, Long> {

	List<SavingsTransaction> findAll();

}
