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

	/**Lade alle FahrplanVerbindungen in der DB
	 * @return
	 */
	public List<FahrplanVerbindungDTO> loadFahrplanVerbindung() {
		List<FahrplanVerbindung> fahrplanVerbindung = em
				.createQuery("SELECT f FROM FahrplanVerbindung f", FahrplanVerbindung.class).getResultList();
		List<FahrplanVerbindungDTO> dtos = new ArrayList<FahrplanVerbindungDTO>();
		fahrplanVerbindung.forEach((fahrVer)-> dtos.add(new FahrplanVerbindungDTO(fahrVer)));
		return dtos;
	}
	
	/**Suche alle FahrplanVerbindungen dieses Fahrplanes
	 * @param fahrplanId Der PK des Fahrplanes
	 * @return
	 */
	public List<FahrplanVerbindungDTO> getFahrplanVerbindungByFahrplan(int fahrplanId){
		List<FahrplanVerbindungDTO> fahrplanVerbindung = loadFahrplanVerbindung();
		List<FahrplanVerbindungDTO> dtos = new ArrayList<FahrplanVerbindungDTO>();
		for(int i = 0; i < fahrplanVerbindung.size(); i++) {
			if(fahrplanVerbindung.get(i).getFahrplan().getId() == fahrplanId) {
				dtos.add(fahrplanVerbindung.get(i));
			}
		}
		return dtos;
	}
	
	/**Persistiere die FahrplanVerbindung in der DB
	 * @param fahrVer
	 */
	public void saveFahrplanVerbindung(FahrplanVerbindungDTO fahrVer) {
		em.merge(fahrVer.toEntity());
	}
}
