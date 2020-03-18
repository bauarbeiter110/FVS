package dao;

import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.metamodel.EntityType;

import entities.*;

@Stateless
@LocalBean
public class UserDao {
	
	@PersistenceContext(unitName = "fvs")
	private EntityManager em;
		
	public List <Haltestelle> loadHaltestellen(){
		System.out.println("DAO Load Haltestellen");		
		return em.createQuery("SELECT h FROM Haltestelle h",Haltestelle.class).getResultList();
		//return null;
	}
	
	public List<User> loadUsers() {
		System.out.println("DAO Load User");
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}
	
	public void gibMetaAus() {
		Set<EntityType<?>> tabellen = em.getMetamodel().getEntities();
    	for(EntityType tabelle : tabellen) {
    		System.out.println(tabelle.toString());
    	}
	}
	
	public void saveUser(User user) {
		em.merge(user);
	}
	
	public void deleteUser(User user) {
		em.remove(user);
	}
}
