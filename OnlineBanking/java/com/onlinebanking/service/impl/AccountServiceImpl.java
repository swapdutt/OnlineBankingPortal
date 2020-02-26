package com.onlinebanking.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebanking.dao.PrimaryAccountDao;
import com.onlinebanking.dao.SavingsAccountDao;
import com.onlinebanking.domain.details.PrimaryAccount;
import com.onlinebanking.domain.details.PrimaryTransaction;
import com.onlinebanking.domain.details.SavingsAccount;
import com.onlinebanking.domain.details.SavingsTransaction;
import com.onlinebanking.domain.details.User;
import com.onlinebanking.service.AccountService;
import com.onlinebanking.service.TransactionService;
import com.onlinebanking.service.UserService;

@Service
public class AccountServiceImpl implements AccountService {

	private static int nextAccountNumber = 11223145;

	@Autowired
	private PrimaryAccountDao primaryAccountDao;

	@Autowired
	private SavingsAccountDao savingsAccountDao;

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@Override
	public PrimaryAccount createPrimaryAccount() {
		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountNumber(accountGen());
		primaryAccount.setAccoutnBalance(new BigDecimal(0.0));

		primaryAccountDao.save(primaryAccount);

		return primaryAccountDao.findAccountNumber(primaryAccount.getAccountNumber());

	}

	@Override
	public SavingsAccount createSavingsAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountNumber(accountGen());
		savingsAccount.setAccountBalance(new BigDecimal(0.0));

		savingsAccountDao.save(savingsAccount);

		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
	}

	@Override
	public void deposit(String accountType, double amount, Principal principal) {
		User user = userService.findByUsername(principal.getName());

		if (accountType.equalsIgnoreCase("Primary")) {
			PrimaryAccount primaryAccount = new PrimaryAccount();
			primaryAccount.setAccoutnBalance(primaryAccount.getAccoutnBalance().add(new BigDecimal(amount)));

			primaryAccountDao.save(primaryAccount);

			Date date = new Date();

			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account",
					"Account", "Finished", amount, primaryAccount.getAccoutnBalance(), primaryAccount);

			transactionService.savePrimaryDepositTransaction(primaryTransaction);

		} else if (accountType.equalsIgnoreCase("Savings")) {

			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));

			savingsAccountDao.save(savingsAccount);

			Date date = new Date();

			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to Savings Account",
					"Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);

			transactionService.saveSavingsDepositTransaction(savingsTransaction);

		}

	}

	@Override
	public void withdraw(String accountType, double amount, Principal principal) {
		User user = userService.findByUsername(principal.getName());

		if (accountType.equalsIgnoreCase("Primary")) {
			PrimaryAccount primaryAccount = new PrimaryAccount();
			primaryAccount.setAccoutnBalance(primaryAccount.getAccoutnBalance().subtract(new BigDecimal(amount)));

			primaryAccountDao.save(primaryAccount);

			Date date = new Date();

			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account",
					"Account", "Finished", amount, primaryAccount.getAccoutnBalance(), primaryAccount);

			transactionService.savePrimaryDepositTransaction(primaryTransaction);

		} else if (accountType.equalsIgnoreCase("Savings")) {

			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));

			savingsAccountDao.save(savingsAccount);

			Date date = new Date();

			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from Savings Account",
					"Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);

			transactionService.saveSavingsDepositTransaction(savingsTransaction);

		}

	}

	private int accountGen() {
		return ++nextAccountNumber;
	}

}
