package dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;

import dto.FahrplanDTO;
import dto.FahrplanVerbindungDTO;
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
	
	@Inject
	FahrplanVerbindungDao fahrVerDao;

	public List<FahrplanDTO> loadFahrplaene() {
		List<Fahrplan> fahrplaene = em.createQuery("SELECT f FROM Fahrplan f", Fahrplan.class).getResultList();
		List<FahrplanDTO> dtos = new ArrayList<FahrplanDTO>();
		for (Fahrplan fahr : fahrplaene) {
			if ((fahr.getStarthaltestelle() == null) || (fahr.getZielhaltestelle() == null)) {
				dtos.add(new FahrplanDTO(fahr.getId(), fahr.getLinienname()));
			} else {
				dtos.add(new FahrplanDTO(fahr));
			}
		}
		return dtos;
	}
	
	public void saveFahrplan(FahrplanDTO fahrplan) {
		em.merge(fahrplan.toEntity());
		em.flush();
	}

	public void createFahrplan(String name) {
		em.merge(new Fahrplan(name));
	}

	public void deleteFahrplan(int fahrplanId) {
		em.remove(em.find(Fahrplan.class, fahrplanId));
	}

	public FahrplanDTO getFahrplanById(int id) {
		Fahrplan fahr = em.find(Fahrplan.class, id);
		if ((fahr.getStarthaltestelle() == null) || (fahr.getZielhaltestelle() == null)) {
			return new FahrplanDTO(fahr.getId(), fahr.getLinienname());
		} else {
			return new FahrplanDTO(fahr);
		}
	}
	
	public VerbindungDTO getlastVerbindung(int fahrplanId){
		FahrplanDTO fahr = getFahrplanById(fahrplanId);
		List <FahrplanVerbindungDTO> fahrver = fahrVerDao.getFahrplanVerbindungByFahrplan(fahrplanId);
		FahrplanVerbindungDTO dto = fahrver.get(0);
		for(int i = 0; i < fahrver.size(); i++) {
			if(dto.getReinfolge() < fahrver.get(i).getReinfolge()) {
				dto = fahrver.get(i);
			}
		}
		return dto.getVerbindung();
	}
}
