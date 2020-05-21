package controller;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDao;
import dto.UserDTO;

import javax.enterprise.context.*;

@Named(value = "SessionUser")
@SessionScoped
public class SessionUser implements Serializable {

	private static final long serialVersionUID = 1L;
	String name;

	@Inject
	UserDao userDao;

	UserDTO sessionUser;

	public String login() {
		UserDTO user = userDao.findUserByName(name);
		if (user == null) {
			System.out.println("User nicht gefunden");
			return "LoginFail";
		}
		sessionUser = user;
		return "LoginSuccess";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserDTO getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(UserDTO sessionUser) {
		this.sessionUser = sessionUser;
	}

}
