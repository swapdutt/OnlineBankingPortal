package com.onlinebanking.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlinebanking.dao.RoleDao;
import com.onlinebanking.dao.UserDao;
import com.onlinebanking.domain.details.User;
import com.onlinebanking.domain.role.UserRole;
import com.onlinebanking.service.AccountService;
import com.onlinebanking.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AccountService accountService;

	@Override
	public User findByUsername(String username) {
		return userDao.findByName(username);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean checkUserExists(String username, String email) {
		if (checkUsernameExists(username) || checkEmailExists(username)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkUsernameExists(String username) {
		if (findByUsername(username) != null) {
			return true;
		}
		return false;

	}

	@Override
	public boolean checkEmailExists(String email) {
		if (findByEmail(email) != null) {
			return true;
		}
		return false;
	}

	@Override
	public void save(User user) {
		userDao.save(user);

	}

	@Override
	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userDao.findByName(user.getUsername());

		if (localUser != null) {
			log.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
			for (UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);

			user.setPrimaryAccount(accountService.createPrimaryAccount());
			user.setSavingsAccount(accountService.createSavingsAccount());

			localUser = userDao.save(user);
		}

		return localUser;
	}

	@Override
	public User saveUser(User user) {
		return userDao.save(user);
	}

	@Override
	public List<User> findUserList() {
		return userDao.findAll();
	}

	@Override
	public void enableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(true);
		userDao.save(user);

	}

	@Override
	public void disableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(false);
		System.out.println(user.isEnabled());
		userDao.save(user);
		System.out.println(username + " is disabled");

	}

}
