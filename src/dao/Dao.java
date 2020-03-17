package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.persistence.metamodel.EntityType;

import entities.*;

@Stateless
@LocalBean
public class Dao {
		
	public List <Haltestelle> loadHaltestellen(EntityManager em){
		System.out.println("DAO Load Haltestellen");		
		return em.createQuery("SELECT h FROM Haltestelle h",Haltestelle.class).getResultList();
		//return null;
	}
	
	public List<User> loadUsers(EntityManager em) {
		System.out.println("DAO Load User");
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}
	
	public void gibMetaAus(EntityManager em) {
		Set<EntityType<?>> tabellen = em.getMetamodel().getEntities();
    	for(EntityType tabelle : tabellen) {
    		System.out.println(tabelle.toString());
    	}
	}
}
