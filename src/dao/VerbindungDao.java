package dao;

import java.io.Serializable;
import java.sql.Time;
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
	
	@Inject 
	FahrplanVerbindungDao fahrVerDao;

	/**Persistiere die VerbindungDTO als Verbindung in der DB
	 * @param verbindung die zu persistierende VerbindungDTO
	 */
	public void saveVerbindung(VerbindungDTO verbindung) {
		em.merge(verbindung.toEntity());
	}

	/**Finde die Verbindung innerhalb der DB
	 * @param verbindungId Der PK der gesuchtenVerbindung
	 * @return VerbindungDTO der gesuchten verbindung.
	 */
	public VerbindungDTO findVerbindungById(int verbindungId) {
		return new VerbindungDTO(em.find(Verbindung.class, verbindungId));
	}
	
	/**Bekomme die letzte Verbindung dieses Fahrplanes. 
	 * @param fahrplanId PK des Fahrplanes
	 * @return VerbindungDTO die die größte Reinfolge hat.
	 */
	public VerbindungDTO getlastVerbindung(int fahrplanId){
		List <FahrplanVerbindungDTO> fahrver = fahrVerDao.getFahrplanVerbindungByFahrplan(fahrplanId);
		FahrplanVerbindungDTO dto = fahrver.get(0);
		for(int i = 0; i < fahrver.size(); i++) {
			if(dto.getReinfolge() < fahrver.get(i).getReinfolge()) {
				dto = fahrver.get(i);
			}
		}
		return dto.getVerbindung();
	}

	/**Überprüfe ob es zwischen diesen Haltestellen eine Verbindung gibt. Die Richtung ist irrelevant.
	 * @param halt1 Eine der beiden Haltestellen.
	 * @param halt2 Die zweite Haltestelle.
	 * @return Sofern sie existiert, die VerbindungDTO
	 */
	public VerbindungDTO getVerbindungByHaltestellen(HaltestelleDTO halt1, HaltestelleDTO halt2) {
		List<VerbindungDTO> ver = loadVerbindung();
		for (int i = 0; i < ver.size(); i++) {
			VerbindungDTO verbindung = ver.get(i);
			if (verbindung.getUrsprung().getId() == halt1.getId() && verbindung.getZiel().getId() == halt2.getId()) {
				return verbindung;
			}
			if (verbindung.getUrsprung().getId() == halt2.getId() && verbindung.getZiel().getId() == halt1.getId()) {
				return verbindung;
			}
		}
		return null;
	}

	/**Lade alle Verbindungen der DB
	 * @return die VerbindungDTOs aller Verbindungen 
	 */
	public List<VerbindungDTO> loadVerbindung() {
		List<Verbindung> verbindungen = em.createQuery("SELECT v FROM Verbindung v", Verbindung.class).getResultList();
		List<VerbindungDTO> dtos = new ArrayList<VerbindungDTO>();
		verbindungen.forEach((ver) -> dtos.add(new VerbindungDTO(ver)));
		return dtos;
	}
	
	/**Gibt die Dauer der Verbindungen zurück, die zu diesem Fahrplan gehören. 
	 * @param fahrplanId Der PK des Fahrplanes
	 * @return Eine Liste aller Fahrtzeiten dieses Fahrplanes
	 */
	public List<Time> getSortedTimeByFahrplanId(int fahrplanId){
		List<Time> dtos = new ArrayList<Time>();
		List<FahrplanVerbindungDTO> fahrplanVerbindungen = fahrVerDao.getFahrplanVerbindungByFahrplan(fahrplanId);
		fahrplanVerbindungen.forEach((ver)-> dtos.add(ver.getVerbindung().getDauer()));	
		return dtos;
	}

	/** Gibt alle Haltestellen wieder, in der Reinfolge in der sie von der gewünschten Linie angefahren werden.
	 * @param fahrplanId Der PK des Fahrplanes
	 * @return
	 */
	public List<HaltestelleDTO> getSortedHaltestellenByFahrplanId(int fahrplanId) {
		List<HaltestelleDTO> dtos = new ArrayList<HaltestelleDTO>();
		// Alle FahrplanVerbindungen dieses Fahrplanes
		List<FahrplanVerbindungDTO> verbindungen = fahrVerDao.getFahrplanVerbindungByFahrplan(fahrplanId);
		if (verbindungen.size() == 0) {
			// Wenn keine Verbindungen zu diesem Fahrplan gehören, dann gebe eine leere
			// Liste zurück.
			return dtos;
		}
		if (verbindungen.size() == 1) {
			dtos.add(verbindungen.get(0).getVerbindung().getUrsprung());
			dtos.add(verbindungen.get(0).getVerbindung().getZiel());
			return dtos;
		}
		
		// verbindungen enthält mindestens 2 FahrplanVerbindungen.
		// Und nur noch Verbindungen die zu diesem Fahrplan gehören. Diese müssen noch
		// nach der Reinfolge sortiert werden
		List<VerbindungDTO> sort = new ArrayList<VerbindungDTO>();
		verbindungen.forEach((ver) -> sort.add(ver.getVerbindung()));
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
					UrsprungIstLetztesElement = true;
				}
			} else {
				// Das Ziel der letzten Verbindung ist das letzte Element in dtos
				if (last.getZiel().getId() == now.getUrsprung().getId()) {
					dtos.add(now.getZiel());
					UrsprungIstLetztesElement = false;
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

	/**Finde alle Fahrpläne, die diese Haltestelle anfahren
	 * @param haltestelleId PK der Haltestelle.
	 * @return
	 */
	public List<FahrplanDTO> getFarhplaeneByHaltestelleId(int haltestelleId) {
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
		List<FahrplanVerbindungDTO> fv = fahrVerDao.loadFahrplanVerbindung();
		List<FahrplanDTO> dto = new ArrayList<FahrplanDTO>();
		for (int i = 0; i < fv.size(); i++) {
			for (int j = 0; j < ver.size(); j++) {
				// Wenn die Verbindung in einem Fahrplan verwendet wird
				if (fv.get(i).getVerbindung().getId() == ver.get(j).getId()) {
					// Nur ergänzen, wenn der Fahrplan noch nicht in den dto vorhanden ist.
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
