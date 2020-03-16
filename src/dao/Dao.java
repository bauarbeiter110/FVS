package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.User;


public class Dao {

	@PersistenceContext(unitName = "fvs")
	private EntityManager em;
	
	public List <User> loadUsers(){
		System.out.println("DAO Load Users");
		return em.createQuery("SELECT u FROM USER u",User.class).getResultList();
	}
}
