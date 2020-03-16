package controller;
import java.util.List;

import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;

import dao.Dao;
import entities.User;

@Named(value = "Check")
@RequestScoped
public class Check {
	
	String message = "Hy";
	Dao dao = new Dao();
	
	@Inject
	private User user = new User();

    public Check() {
        super();
        System.out.println("Check gestartet");
    }
    
    public String getUser() {
    	System.out.println("Load User gewählt");
    	//List <User> users = dao.loadUsers();
    	//if(users.isEmpty()) {
    		return "Keine User vorhanden";
    	//}
    	//else {
    	//	this.user = users.get(0);
    	//	return user.toString();
    	//}
    }
    public void setUser(User user) {
		this.user = user;
	}
    
    public String getMessage() {
    	return message;
    }
    
    public void setMessage(String message) {
    	this.message = message;
    }
	
}
