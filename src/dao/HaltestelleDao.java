package dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.HaltestelleDTO;
import entities.Haltestelle;
import entities.User;

@Stateless
@LocalBean
public class HaltestelleDao implements Serializable {

	
	@PersistenceContext(unitName = "fvs")
	private EntityManager em;
	
	public List<HaltestelleDTO> loadHaltestellen() {
		return em.createQuery("SELECT h FROM Haltestelle h", HaltestelleDTO.class).getResultList();
	}
	
	public void saveHaltestelle(String name) {
		List <Haltestelle> haltestellen = em.createQuery("SELECT h FROM Haltestelle h", Haltestelle.class).getResultList();
		boolean changeHaltestelle = false;
		for(Haltestelle haltestelle: haltestellen) {
			if(haltestelle.getName().equals(name)) {		
				em.merge(haltestelle);
				changeHaltestelle = true;
			}
		}
	// Der User wurde nicht im Persistenz-Kontext gefunden. Es ist ein neuer User
		if (changeHaltestelle == false) {
			em.merge(new Haltestelle(name));
		}
	}
	
	public void deleteHaltestelle(int haltestelleId) {
		em.remove(em.find(Haltestelle.class, haltestelleId));
	}	
}
