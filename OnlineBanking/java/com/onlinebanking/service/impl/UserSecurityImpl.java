package com.onlinebanking.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onlinebanking.dao.UserDao;
import com.onlinebanking.domain.details.User;

@Service
public class UserSecurityImpl implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserSecurityImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByName(username);
		if (user == null) {
			log.warn("Username {} not found!!!", username);
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		return user;
	}

}
