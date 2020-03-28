package controller;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDao;
import entities.User;

import javax.enterprise.context.*;

@Named(value = "SessionUser")
@SessionScoped
public class SessionUser implements Serializable {

	private static final long serialVersionUID = 1L;
	String name;

	@Inject
	UserDao userDao;

	User sessionUser;

	public String login() {
		User user = userDao.findUserByName(name);
		if (user == null) {
			System.out.println("User nicht gefunden");
			return "LoginFail";
		}
		sessionUser=user;
		return "LoginSuccess";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}

}
