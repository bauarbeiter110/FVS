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
	
	public List<User> loadUsers() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}
	
	public void saveUser(User user) {
		em.merge(user);
	}
	
	public void deleteUser(User user) {
		em.remove(user);
	}
	
	public void gibMetaAus() {
		Set<EntityType<?>> tabellen = em.getMetamodel().getEntities();
    	for(EntityType tabelle : tabellen) {
    		System.out.println(tabelle.toString());
    	}
	}
}
