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
import dto.VerbindungDTO;
import entities.Fahrplan;
import entities.FahrplanVerbindung;
import entities.Haltestelle;
import entities.User;
import entities.Verbindung;

@Stateless
@LocalBean
public class VerbindungDao implements Serializable {

	@PersistenceContext(unitName = "fvs")
	private EntityManager em;
	
	public void saveVerbindung(VerbindungDTO ver) {
		Verbindung verbin = ver.toEntity();
		em.merge(verbin);
	}
	
	public List<VerbindungDTO> loadVerbindung() {		
		List<Verbindung> users = em.createQuery("SELECT v FROM Verbindung v", Verbindung.class).getResultList();
		List<VerbindungDTO> dtos = new ArrayList <VerbindungDTO>();
		users.forEach((ver) -> dtos.add(new VerbindungDTO(ver)));
		return dtos;		
	}

	public List<HaltestelleDTO> getHaltestellenByFahrplanId(int fahrplanId) {
		List<HaltestelleDTO> dtos = new ArrayList<HaltestelleDTO>();
		List<FahrplanVerbindung> verbindungen = em
				.createQuery("SELECT f FROM FahrplanVerbindung f", FahrplanVerbindung.class).getResultList();
		for (int i = 0; i < verbindungen.size(); i++) {
			// Wenn die FahrplanVerbindung zu dem Fahrplan gehört, der dargestellt werden
			// soll.
			if (verbindungen.get(i).getFahrplan().getId() == fahrplanId) {
				if (i == 0) {
					dtos.add(new HaltestelleDTO(verbindungen.get(i).getVerbindung().getUrsprung()));
				}
				dtos.add(new HaltestelleDTO(verbindungen.get(i).getVerbindung().getZiel()));
			}
		}
		return dtos;
	}

	public List<FahrplanDTO> getFarhplaeneByHaltestelleId(int haltestelleId) {
		// TODO: Beide Abfragen könnten mithilfe einer Abfrage
		// Haltestelle OUTER JOIN Verbindung INNER JOIN VerbindungFahrplan
		// ersetzt werden.
		List<Verbindung> verbindungen = em.createQuery("SELECT v FROM Verbindung v", Verbindung.class).getResultList();
		List<Verbindung> ver = new ArrayList<Verbindung>();
		for (int i = 0; i < verbindungen.size(); i++) {
			// Wenn die Haltestelle entweder als Ursprung noch Ziel der Verbindung fungiert,
			// so ist sie von Bedeutung.
			if (verbindungen.get(i).getUrsprung().getId() == haltestelleId
					|| verbindungen.get(i).getZiel().getId() == haltestelleId) {
				ver.add(verbindungen.get(i));
			}
		}
		List<FahrplanVerbindung> fv = em.createQuery("SELECT f FROM FahrplanVerbindung f", FahrplanVerbindung.class)
				.getResultList();
		List<FahrplanDTO> dto = new ArrayList<FahrplanDTO>();
		for (int i = 0; i < fv.size(); i++) {
			for (int j = 0; j < ver.size(); j++) {
				// Wenn die Verbindung in einem Fahrplan verwendet wird
				if (fv.get(i).getVerbindung().getId() == ver.get(j).getId()) {
					// Nur ergänzen, senn der Fahrplan noch nicht in den dto vorhanden ist.
					boolean exists = true;
					for (int k = 0; k < dto.size(); k++) {
						if(dto.get(k).getId() == fv.get(i).getFahrplan().getId()) {
							exists = false;
						}
					}
					if(exists) {
						dto.add(new FahrplanDTO(fv.get(i).getFahrplan().getId(),
								fv.get(i).getFahrplan().getLinienname()));
					}
				}
			}
		}
		return dto;
	}
}
