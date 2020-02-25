package com.onlinebanking.service;

import java.security.Principal;

import com.onlinebanking.domain.details.PrimaryAccount;
import com.onlinebanking.domain.details.SavingsAccount;

public interface AccountService {

	PrimaryAccount createPrimaryAccount();

	SavingsAccount createSavingsAccount();

	void deposit(String accountType, double amount, Principal principal);

	void withdraw(String accountType, double amount, Principal principal);

}
