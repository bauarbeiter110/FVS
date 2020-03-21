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
		System.out.println("name: " + this.name);
		User newUser = new User(this.name);
		System.out.println("Bind User: " + newUser.toString());
		userDao.saveUser(newUser);
	}
	
	public void delete(User user) {
		System.out.println("Delete ausgwaehlt");
		System.out.println("User: " + user.toString());
		// userDao.remove();
	}
	
	public void delete(String user) {
		System.out.println("Delete ausgwaehlt");
		System.out.println("String: " + user);
		// userDao.remove();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
