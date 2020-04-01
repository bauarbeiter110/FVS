package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Haltestelle;

@Stateless
@LocalBean
public class HaltestelleDao implements Serializable {

	
	@PersistenceContext(unitName = "fvs")
	private EntityManager em;
	
	public List<Haltestelle> loadUsers() {
		return em.createQuery("SELECT h FROM Haltestelle h", Haltestelle.class).getResultList();
	}
	
	public void saveHaltestelle(Haltestelle haltestelle) {
		em.merge(haltestelle);
	}
	
	public void deleteUser(int userId) {
		em.remove(em.find(Haltestelle.class, userId));
	}	
}
