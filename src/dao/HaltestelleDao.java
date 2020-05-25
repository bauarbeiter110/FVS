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
import dto.HaltestelleDTO;
import dto.UserDTO;
import dto.VerbindungDTO;
import entities.Fahrplan;
import entities.Haltestelle;
import entities.User;

@Stateless
@LocalBean
public class HaltestelleDao implements Serializable {

	@PersistenceContext(unitName = "fvs")
	private EntityManager em;

	@Inject
	VerbindungDao verDao;

	/**
	 * Lade alle Haltestellen der DB
	 * 
	 * @return
	 */
	public List<HaltestelleDTO> loadHaltestellen() {
		List<HaltestelleDTO> dtos = new ArrayList<HaltestelleDTO>();
		em.createQuery("SELECT h FROM Haltestelle h", Haltestelle.class).getResultList()
				.forEach((haltestelle) -> dtos.add(new HaltestelleDTO(haltestelle)));
		return dtos;
	}

	/**
	 * Finde die Haltestelle mit dieser Id
	 * 
	 * @param haltestelleId PK der Haltestelle
	 * @return
	 */
	public HaltestelleDTO findHaltestelleById(int haltestelleId) {
		return new HaltestelleDTO(em.find(Haltestelle.class, haltestelleId));
	}

	/**
	 * Erstelle eine neue Haltestelle
	 * 
	 * @param name Name der neuen Haltestelle
	 */
	public void createHaltestelle(String name) {
		em.merge(new Haltestelle(name));
	}

	/**Suche alle Haltestelle die von der betrachteten Haltestelle aus erreichbar sind.
	 * @param haltestellenId PK der betrachteten Haltestelle.
	 * @return
	 */
	public List<HaltestelleDTO> getHaltestelleByHaltestelle(int haltestellenId){
		List<VerbindungDTO> ver = verDao.loadVerbindung();
		List<HaltestelleDTO> halt = new ArrayList<HaltestelleDTO>();
		for(int i = 0; i < ver.size(); i++) {
			VerbindungDTO verbindung = ver.get(i);
			if(verbindung.getUrsprung().getId() == haltestellenId) {
				//Die Haltestelle ist Ursprung dieser Verbindung/ Das Ziel ist dieser Verbindung ist von ihr aus erreichbar.
				halt.add(verbindung.getZiel());
			}
			else {
				if(verbindung.getZiel().getId() == haltestellenId) {
					//Die Haltestelle ist Ziel dieser Verbindung/ Der Ursprung dieser Verbindung ist von ihr aus erreichbar.
					halt.add(verbindung.getUrsprung());
				}
				//Die betrachtete Haltestelle ist nicht Teil dieser Verbindung.
			}
		}
		return halt;
	}
}
