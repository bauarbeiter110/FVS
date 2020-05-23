package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.FahrplanDTO;
import dto.HaltestelleDTO;
import dto.UserDTO;
import entities.Fahrplan;
import entities.Haltestelle;
import entities.User;

@Stateless
@LocalBean
public class HaltestelleDao implements Serializable {

	@PersistenceContext(unitName = "fvs")
	private EntityManager em;

	public List<HaltestelleDTO> loadHaltestellen() {
		List<HaltestelleDTO> dtos = new ArrayList<HaltestelleDTO>();
		em.createQuery("SELECT h FROM Haltestelle h", Haltestelle.class).getResultList()
				.forEach((haltestelle) -> dtos.add(new HaltestelleDTO(haltestelle)));
		return dtos;
	}

	public void deleteHaltestelle(int haltestelleId) {
		em.remove(em.find(Haltestelle.class, haltestelleId));
	}

	public void createHaltestelle(String name) {
		em.merge(new Haltestelle(name));

	}
}
