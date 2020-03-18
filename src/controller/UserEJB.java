package controller;

import java.util.List;

import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDao;
import entities.User;

@Named(value = "UserEJB")
@ApplicationScoped
public class UserEJB {
	
	@Inject
	UserDao userDao;
	
	public List <User>getUsers(){
		return userDao.loadUsers();
	}
	
	public void add() {
		User u = new User();
		u.setName("John Doe");
		userDao.saveUser(u);
	}

}
