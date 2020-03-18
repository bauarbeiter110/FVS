package controller;

import javax.enterprise.context.*;
import javax.inject.Named;

import dao.UserDao;
import entities.*;

@Named(value = "Check")
@ApplicationScoped
public class Check {
	
	String message = "Hy";
	
	private User user;
		
	UserDao dao = new UserDao();
	   
    public String getUser() {
    	//System.out.println("Load User gewaehlt");
    	//dao.gibMetaAus();
    	//System.out.println(dao.loadHaltestellen().toString());
    	return "Laden erfolgreich";
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
