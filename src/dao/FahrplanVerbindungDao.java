package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;

import dto.FahrplanDTO;
import dto.FahrplanVerbindungDTO;
import dto.HaltestelleDTO;
import dto.VerbindungDTO;
import entities.FahrplanVerbindung;
import entities.Verbindung;

@Stateless
@LocalBean
public class FahrplanVerbindungDao {

	@PersistenceContext(unitName = "fvs")
	private EntityManager em;

	public List<FahrplanVerbindungDTO> loadFahrplanVerbindung() {
		List<FahrplanVerbindung> fahrplanVerbindung = em
				.createQuery("SELECT f FROM FahrplanVerbindung f", FahrplanVerbindung.class).getResultList();
		List<FahrplanVerbindungDTO> dtos = new ArrayList<FahrplanVerbindungDTO>();
		for(int i = 0; i< fahrplanVerbindung.size(); i++) {
			FahrplanVerbindung fahrVer = fahrplanVerbindung.get(i);
			VerbindungDTO ver = new VerbindungDTO(fahrVer.getVerbindung());
			FahrplanDTO fahr =  new FahrplanDTO(fahrVer.getFahrplan(), new HaltestelleDTO(fahrVer.getFahrplan().getStarthaltestelle()), new HaltestelleDTO(fahrVer.getFahrplan().getZielhaltestelle()));
			dtos.add(new FahrplanVerbindungDTO(fahrVer.getReinfolge(), fahr, ver));
		}
		return dtos;
	}
	
	public void saveFahrplanVerbindung(FahrplanVerbindungDTO fahrVer) {
		em.merge(fahrVer.toEntity());
	}
}
