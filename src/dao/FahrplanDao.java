package dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;

import dto.FahrplanDTO;
import dto.HaltestelleDTO;
import dto.VerbindungDTO;
import entities.Fahrplan;
import entities.Haltestelle;
import entities.Verbindung;

@Stateless
@LocalBean
public class FahrplanDao {

	@PersistenceContext(unitName = "fvs")
	private EntityManager em;

	public List<FahrplanDTO> loadFahrplaene() {
		List<Fahrplan> fahrplaene = em.createQuery("SELECT f FROM Fahrplan f", Fahrplan.class).getResultList();
		List<FahrplanDTO> dtos = new ArrayList<FahrplanDTO>();
		for (Fahrplan fahr : fahrplaene) {
			if ((fahr.getStarthaltestelle() == null) && (fahr.getZielhaltestelle() == null)) {
				dtos.add(new FahrplanDTO(fahr.getId(), fahr.getLinienname()));
			} else {
				HaltestelleDTO start = new HaltestelleDTO(fahr.getStarthaltestelle());
				HaltestelleDTO ziel = new HaltestelleDTO(fahr.getZielhaltestelle());
				dtos.add(new FahrplanDTO(fahr, start, ziel));
			}
		}
		return dtos;
	}

	public void createFahrplan(String name) {
		em.merge(new Fahrplan(name));
	}

	public void deleteFahrplan(int fahrplanId) {
		em.remove(em.find(Fahrplan.class, fahrplanId));
	}

	public FahrplanDTO getFahrplanById(int id) {
		Fahrplan fahr = em.find(Fahrplan.class, id);
		HaltestelleDTO start = new HaltestelleDTO(fahr.getStarthaltestelle());
		HaltestelleDTO ziel = new HaltestelleDTO(fahr.getZielhaltestelle());
		return new FahrplanDTO(fahr, start, ziel);
	}
}
