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

	public List<HaltestelleDTO> getHaltestellenByFahrplanId(int fahrplanId) {
		List<HaltestelleDTO> dtos = new ArrayList<HaltestelleDTO>();
		List<FahrplanVerbindung> verbindungen = em
				.createQuery("SELECT f FROM FahrplanVerbindung f", FahrplanVerbindung.class).getResultList();
		for (int i = 0; i < verbindungen.size(); i++) {
			//Wenn die FahrplanVerbindung zu dem Fahrplan gehört, der dargestellt werden soll.
			if (verbindungen.get(i).getFahrplan().getId() == fahrplanId) {
				if(i == 0) {
					dtos.add(new HaltestelleDTO(verbindungen.get(i).getVerbindung().getUrsprung()));
				}
				dtos.add(new HaltestelleDTO(verbindungen.get(i).getVerbindung().getZiel()));
			}
		}
		dtos.forEach((haltestelle) -> System.out.println(haltestelle.getName()));
		return dtos;
	}
}
