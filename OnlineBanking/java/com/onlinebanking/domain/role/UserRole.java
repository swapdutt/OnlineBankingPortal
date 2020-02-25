package com.onlinebanking.domain.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.onlinebanking.domain.details.User;

@Entity
@Table(name = "user_role")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_role_id")
	private long userRoleId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

	public UserRole() {
		// TODO Auto-generated constructor stub
	}

	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	public long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserRole [userRoleId=" + userRoleId + ", user=" + user + ", role=" + role + "]";
	}

}
