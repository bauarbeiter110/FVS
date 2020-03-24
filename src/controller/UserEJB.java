package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.*;
import javax.faces.model.SelectItem;
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
	int userId;
	
	List<User> users;
	

	@PostConstruct
	public void init() {
		users = userDao.loadUsers();
	}

	public void add() {
		System.out.println("name: " + this.name);
		User newUser = new User(this.name);
		System.out.println("Bind User: " + newUser.toString());
		userDao.saveUser(newUser);
	}

	public void delete() {
		System.out.println("Delete ausgwaehlt");
		System.out.println("Bind: " + this.userId);
		for (User user : users) {
			if(user.getId()==this.userId) {
				System.out.println(user.toString());
				userDao.deleteUser(userId);
				
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
