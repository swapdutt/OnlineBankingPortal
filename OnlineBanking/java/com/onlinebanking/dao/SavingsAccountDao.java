package com.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;

import com.onlinebanking.domain.details.SavingsAccount;

public interface SavingsAccountDao extends CrudRepository<SavingsAccount, Long> {

	SavingsAccount findByAccountNumber(int accountNumber);

}
