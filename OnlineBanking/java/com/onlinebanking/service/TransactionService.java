package com.onlinebanking.service;

import java.security.Principal;
import java.util.List;

import com.onlinebanking.domain.details.PrimaryAccount;
import com.onlinebanking.domain.details.PrimaryTransaction;
import com.onlinebanking.domain.details.Recipient;
import com.onlinebanking.domain.details.SavingsAccount;
import com.onlinebanking.domain.details.SavingsTransaction;

public interface TransactionService {

	List<PrimaryTransaction> findPrimaryTransactionList(String username);

	List<SavingsTransaction> findSavingsTransactionList(String username);

	void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);

	void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

	void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);

	void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);

	void betweenAccountsTransfer(String firstName, String transferTo, String amount, PrimaryAccount primaryAccount,
			SavingsAccount savingsAccount) throws Exception;

	List<Recipient> findRecipientList(Principal principal);

	Recipient saveRecipient(Recipient recipient);

	Recipient findRecipientByName(String recipientName);

	void deleteRecipientByName(String recipientName);

	void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount,
			SavingsAccount savingsAccount);

}