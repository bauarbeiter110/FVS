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

	/** Gibt alle Fahrpläne der DB zurück. Es wird in Fahrpläne mit und ohne (Start/- Zielhaltestelle und Startzeitpunkt) unterschieden.
	 * @return Eine Liste mit allen Fahrplänen der DB als FahplanDTOs
	 */
	public List<FahrplanDTO> loadFahrplaene() {
		List<Fahrplan> fahrplaene = em.createQuery("SELECT f FROM Fahrplan f", Fahrplan.class).getResultList();
		List<FahrplanDTO> dtos = new ArrayList<FahrplanDTO>();
		fahrplaene.forEach((fahr)-> dtos.add(castFahrplan(fahr)));
		return dtos;
	}

	/**Suche mithilfe der ID den entsprechenden Fahrplan in der DB. Es wird in Fahrpläne mit und ohne (Start/- Zielhaltestelle und Startzeitpunkt) unterschieden.
	 * @param id Der PK des Fahrplanes, seine Id
	 * @return sofern vorhanden einen Fahrplan DTO dieses Fahrplanes
	 */
	public FahrplanDTO getFahrplanById(int id) {
		Fahrplan fahr = em.find(Fahrplan.class, id);
		return castFahrplan(fahr);
	}
	
	/**Erstellt aus einem Fahrplan einen FahrplanDTO.
	 * @param fahr Der zu castende Fahrplan
	 * @return Es wird ein FahrplanDTO entweder mit oder ohne (Start/- Zielhaltestelle und Startzeitpunkt) zurückgegeben. 
	 */
	private FahrplanDTO castFahrplan(Fahrplan fahr) {
		if ((fahr.getStarthaltestelle() == null) || (fahr.getZielhaltestelle() == null)) {
			return new FahrplanDTO(fahr.getId(), fahr.getLinienname());
		} else {
			return new FahrplanDTO(fahr);
		}
	}
	
	/** Persistiere den Fahrplan sowohl in dem PersistenceContext und führe einen Commit durch.
	 * @param fahrplan Der zu speichernde FahrplanDTO, der dabei zu einem Fahrplan gecastet wird.
	 */
	public void saveFahrplan(FahrplanDTO fahrplan) {
		em.merge(fahrplan.toEntity());
		em.flush();
	}

	/**Erstelle einen neuen Fahrplan mit einem Namen/ ohne Start/- Zielhaltestelle und Startzeitpunkt
	 * @param name Name des neuen Fahrplanes
	 */
	public void createFahrplan(String name) {
		em.merge(new Fahrplan(name));
	}

	

}
