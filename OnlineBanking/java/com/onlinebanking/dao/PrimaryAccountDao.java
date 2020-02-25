package com.onlinebanking.dao;

import org.springframework.data.repository.CrudRepository;

import com.onlinebanking.domain.details.PrimaryAccount;

public interface PrimaryAccountDao extends CrudRepository<PrimaryAccount, Long> {

	PrimaryAccount findAccountNumber(int accountNumber);

}
