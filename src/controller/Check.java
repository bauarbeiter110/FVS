package controller;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;

import dao.Dao;
import entities.*;

@Named(value = "Check")
@ApplicationScoped
public class Check {
	
	String message = "Hy";
	
	private User user;
	
	@PersistenceContext(unitName = "fvs")
	private EntityManager em;
	
	Dao dao = new Dao();
	   
    public String getUser() {
    	System.out.println("Load User gewaehlt");
    	dao.gibMetaAus(em);
    	System.out.println(dao.loadHaltestellen(em).toString());
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
