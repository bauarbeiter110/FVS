package controller;

import java.util.List;

import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDao;
import entities.User;

@Named(value = "UserEJB")
@RequestScoped
public class UserEJB {

	@Inject
	UserDao userDao;
	
	String name;

	public List<User> getUsers() {
		System.out.println("getUsers");
		return userDao.loadUsers();
	}
	
	public void add() {
		System.out.println("Add name");
		System.out.println("Bind name: " + this.name);
		User u2 = new User(this.name);
		userDao.saveUser(u2);
		System.out.println("Bind User: " + u2.toString());
	}
	
	public void delete() {
		System.out.println("Delete ausgwaehlt");
		// userDao.remove();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
