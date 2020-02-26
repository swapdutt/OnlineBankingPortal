package com.onlinebanking.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebanking.domain.details.PrimaryTransaction;
import com.onlinebanking.domain.details.SavingsTransaction;
import com.onlinebanking.domain.details.User;
import com.onlinebanking.service.TransactionService;
import com.onlinebanking.service.UserService;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/user/all", method = RequestMethod.GET)
	public List<User> userList() {
		return userService.findUserList();
	}

	@RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
	public List<PrimaryTransaction> getPrimaryTransactionList(@RequestParam("username") String username) {
		return transactionService.findPrimaryTransactionList(username);
	}

	@RequestMapping(value = "/user/savings/transaction", method = RequestMethod.GET)
	public List<SavingsTransaction> getSavingsTransactionList(@RequestParam("username") String username) {
		return transactionService.findSavingsTransactionList(username);
	}

	@RequestMapping("/user/{username}/disable")
	public void disableUser(@PathVariable("username") String username) {
		userService.disableUser(username);
	}

}
