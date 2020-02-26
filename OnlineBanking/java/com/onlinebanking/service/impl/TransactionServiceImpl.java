package com.onlinebanking.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebanking.dao.PrimaryAccountDao;
import com.onlinebanking.dao.PrimaryTransactionDao;
import com.onlinebanking.dao.RecipeintDao;
import com.onlinebanking.dao.SavingsAccountDao;
import com.onlinebanking.dao.SavingsTransactionDao;
import com.onlinebanking.domain.details.PrimaryAccount;
import com.onlinebanking.domain.details.PrimaryTransaction;
import com.onlinebanking.domain.details.Recipient;
import com.onlinebanking.domain.details.SavingsAccount;
import com.onlinebanking.domain.details.SavingsTransaction;
import com.onlinebanking.domain.details.User;
import com.onlinebanking.service.TransactionService;
import com.onlinebanking.service.UserService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private UserService userService;

	@Autowired
	private PrimaryTransactionDao primaryTransactionDao;

	@Autowired
	private SavingsTransactionDao savingsTransactionDao;

	@Autowired
	private PrimaryAccountDao primaryAccountDao;

	@Autowired
	private SavingsAccountDao savingsAccountDao;

	@Autowired
	private RecipeintDao recipeintDao;

	@Override
	public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();
		return primaryTransactionList;
	}

	@Override
	public List<SavingsTransaction> findSavingsTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();
		return savingsTransactionList;
	}

	@Override
	public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}

	@Override
	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}

	@Override
	public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
		primaryTransactionDao.save(primaryTransaction);
	}

	@Override
	public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
	}

	@Override
	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
		if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
			primaryAccount.setAccoutnBalance(primaryAccount.getAccoutnBalance().subtract(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));

			primaryAccountDao.save(primaryAccount);
			savingsAccountDao.save(savingsAccount);

			Date date = new Date();

			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
					"Between account transfer from " + transferFrom + " to" + transferTo, "Account", "Finished",
					Double.parseDouble(amount), primaryAccount.getAccoutnBalance(), primaryAccount);
			primaryTransactionDao.save(primaryTransaction);
		} else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
			primaryAccount.setAccoutnBalance(primaryAccount.getAccoutnBalance().add(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));

			primaryAccountDao.save(primaryAccount);
			savingsAccountDao.save(savingsAccount);

			Date date = new Date();

			SavingsTransaction savingsTransaction = new SavingsTransaction(date,
					"Between account transfer from " + transferFrom + " to" + transferTo, "Account", "Finished",
					Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
			savingsTransactionDao.save(savingsTransaction);
		} else {
			throw new Exception("Invalid Transfer");
		}

	}

	@Override
	public List<Recipient> findRecipientList(Principal principal) {
		String username = principal.getName();
		List<Recipient> recipientList = recipeintDao.findAll().stream()
				.filter(recipient -> username.equals(recipient.getUser().getUsername())).collect(Collectors.toList());
		return recipientList;
	}

	@Override
	public Recipient saveRecipient(Recipient recipient) {
		return recipeintDao.save(recipient);
	}

	@Override
	public Recipient findRecipientByName(String recipientName) {
		return recipeintDao.findByName(recipientName);
	}

	@Override
	public void deleteRecipientByName(String recipientName) {
		recipeintDao.deleteByName(recipientName);

	}

	@Override
	public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {
		if (accountType.equalsIgnoreCase("Primary")) {
			primaryAccount.setAccoutnBalance(primaryAccount.getAccoutnBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);

			Date date = new Date();

			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
					"Transfer to recipient " + recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount),
					primaryAccount.getAccoutnBalance(), primaryAccount);
			primaryTransactionDao.save(primaryTransaction);
		} else if (accountType.equalsIgnoreCase("Savings")) {
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);

			Date date = new Date();

			SavingsTransaction savingsTransaction = new SavingsTransaction(date,
					"Transfer to recipient " + recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount),
					savingsAccount.getAccountBalance(), savingsAccount);
			savingsTransactionDao.save(savingsTransaction);
		}

	}

}
