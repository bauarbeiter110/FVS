package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.FahrplanDTO;
import dto.FahrplanVerbindungDTO;
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

	@Inject
	FahrplanDao fahrDao;

	public void saveVerbindung(VerbindungDTO ver) {
		Verbindung verbin = ver.toEntity();
		em.merge(verbin);
	}

	public VerbindungDTO getVerbindungById(int verbindungId) {
		return new VerbindungDTO(em.find(Verbindung.class, verbindungId));
	}

	public VerbindungDTO getVerbindungByHaltestellen(HaltestelleDTO start, HaltestelleDTO ende) {
		List<VerbindungDTO> ver = loadVerbindung();
		VerbindungDTO dto = new VerbindungDTO();
		for (int i = 0; i < ver.size(); i++) {
			VerbindungDTO verbindung = ver.get(i);
			if (verbindung.getUrsprung().getId() == start.getId() && verbindung.getZiel().getId() == ende.getId()) {
				return verbindung;
			}
			if (verbindung.getUrsprung().getId() == ende.getId() && verbindung.getZiel().getId() == start.getId()) {
				return verbindung;
			}
		}
		return null;
	}

	public List<VerbindungDTO> loadVerbindung() {
		List<Verbindung> users = em.createQuery("SELECT v FROM Verbindung v", Verbindung.class).getResultList();
		List<VerbindungDTO> dtos = new ArrayList<VerbindungDTO>();
		users.forEach((ver) -> dtos.add(new VerbindungDTO(ver)));
		return dtos;
	}

	public List<HaltestelleDTO> getSortedHaltestellenByFahrplanId(int fahrplanId) {
		List<HaltestelleDTO> dtos = new ArrayList<HaltestelleDTO>();
		List<FahrplanVerbindung> verbindungen = em.createQuery(
				"SELECT f FROM FahrplanVerbindung f INNER JOIN Verbindung v ON f.Verbindung.id = v.id WHERE f.Fahrplan.id = "
						+ fahrplanId,
				FahrplanVerbindung.class).getResultList();
		if (verbindungen.size() == 0) {
			// Wenn keine Verbindungen zu diesem Fahrplan gehören, dann gebe eine leere
			// Liste zurück.
			return dtos;
		}
		if (verbindungen.size() == 1) {
			dtos.add(new HaltestelleDTO(verbindungen.get(0).getVerbindung().getUrsprung()));
			dtos.add(new HaltestelleDTO(verbindungen.get(0).getVerbindung().getZiel()));
			return dtos;
		}
		
		// verbindungen enthält mindestens 2 FahrplanVerbindungen.
		// Und nur noch Verbindungen die zu diesem Fahrplan gehören. Diese müssen noch
		// nach der Reinfolge sortiert werden
		List<VerbindungDTO> sort = new ArrayList<VerbindungDTO>();
		verbindungen.forEach((ver) -> sort.add(new VerbindungDTO(ver.getVerbindung())));
		boolean UrsprungIstLetztesElement;
		// Festlegung des ersten Elementes in Abhängigkeit des zweiten Eintrages.
		if (sort.get(0).getZiel().getId() == sort.get(1).getUrsprung().getId()
				|| sort.get(0).getZiel().getId() == sort.get(1).getZiel().getId()) {
			// Das Ziel der ersten Verbindung ist ein Bestandteil der zweiten Verbindung.
			// Der Ursprung der ersten Verbindung ist das erste Element der Liste
			dtos.add(sort.get(0).getUrsprung());
			UrsprungIstLetztesElement = true;
		} else {
			// Der Ursprung der ersten Verbindung ist in der zweiten Verbindung vorhanden.
			dtos.add(sort.get(0).getZiel());
			UrsprungIstLetztesElement = false;
		}
		for (int i = 0; i <= sort.size() - 2; i++) {
			VerbindungDTO last = sort.get(i);
			VerbindungDTO now = sort.get(i + 1);
			if (UrsprungIstLetztesElement) {
				// Der Ursprung der letzten Verbindung ist das letzte Element in dtos.
				if (last.getUrsprung().getId() == now.getUrsprung().getId()) {
					dtos.add(now.getZiel());
					UrsprungIstLetztesElement = false;
				} else {
					dtos.add(now.getUrsprung());
				}
			} else {
				// Das Ziel der letzten Verbindung ist das letzte Element in dtos
				if (last.getZiel().getId() == now.getUrsprung().getId()) {
					dtos.add(now.getZiel());
				} else {
					dtos.add(now.getUrsprung());
					UrsprungIstLetztesElement = true;
				}
			}
		}
		// Das letzte Element fehlt noch:
		dtos.add(fahrDao.getFahrplanById(fahrplanId).getZielhaltestelle());
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
						if (dto.get(k).getId() == fv.get(i).getFahrplan().getId()) {
							exists = false;
						}
					}
					if (exists) {
						dto.add(new FahrplanDTO(fv.get(i).getFahrplan().getId(),
								fv.get(i).getFahrplan().getLinienname()));
					}
				}
			}
		}
		return dto;
	}
}
