package controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;

import dao.HaltestelleDao;
import dao.UserDao;
import dao.VerbindungDao;
import dto.HaltestelleDTO;
import dto.UserDTO;
import dto.VerbindungDTO;

@Named(value = "VerbindungEJB")
@ApplicationScoped
public class VerbindungEJB {

	@Inject
	VerbindungDao verbindungDao;

	@Inject
	HaltestelleDao haltDao;

	// Variablen auf die von der xhtml zugegriffen wird
	int ursprungId;
	int zielId;
	List<VerbindungDTO> verbindungen;
	List<HaltestelleDTO> halt;
	int min;

	// Beim Seitenaufbau immer durchgeführt.
	@PostConstruct
	public void init() {
		verbindungen = verbindungDao.loadVerbindung();
	}

	public VerbindungDao getVerbindungDao() {
		return verbindungDao;
	}

	public void setVerbindungDao(VerbindungDao verbindungDao) {
		this.verbindungDao = verbindungDao;
	}

	public String addUrsprung() {
		halt = haltDao.loadHaltestellen();
		// Entfernen der Ursprungshaltestelle aus der Liste der Auswahlmöglichkeiten
		for (int i = 0; i < halt.size(); i++) {
			if (halt.get(i).getId() == ursprungId) {
				halt.remove(i);
				break;
			}
		}
		List<VerbindungDTO> ver2 = new ArrayList<VerbindungDTO>();

		for (int i = 0; i < verbindungen.size(); i++) {
			// Wenn es eine Verbindung Von/Zu der Haltestelle bereits gibt, wird sie aus den
			// Auswahlmöglichkeiten entfernt.
			if (verbindungen.get(i).getUrsprung().getId() == ursprungId) {
				ver2.add(verbindungen.get(i));
				for (int j = 0; j < halt.size(); j++) {
					if (verbindungen.get(i).getZiel().getId() == halt.get(j).getId()) {
						halt.remove(j);
					}
				}
			} else if (verbindungen.get(i).getZiel().getId() == ursprungId) {
				ver2.add(verbindungen.get(i));
				for (int j = 0; j < halt.size(); j++) {
					if (verbindungen.get(i).getUrsprung().getId() == halt.get(j).getId()) {
						halt.remove(j);
					}
				}
			}
		}
		verbindungen = ver2;
		return "verbindungSpeziell.xhtml";
	}

	public String addVerbindung() {
		@SuppressWarnings("deprecation")
		Time t = new Time(0, min, 0);
		VerbindungDTO dto = new VerbindungDTO(t, haltDao.findHaltestelleById(ursprungId), haltDao.findHaltestelleById(zielId));
		verbindungDao.saveVerbindung(dto);
		return zurueck();
	}
	
	public String zurueck() {
		init();
		return "verbindungUebersicht.xhtml";
	}

	public int getUrsprungId() {
		return ursprungId;
	}

	public void setUrsprungId(int ursprungId) {
		this.ursprungId = ursprungId;
	}

	public int getZielId() {
		return zielId;
	}

	public void setZielId(int zielId) {
		this.zielId = zielId;
	}

	public List<VerbindungDTO> getVerbindungen() {
		return verbindungen;
	}

	public void setVerbindungen(List<VerbindungDTO> verbindungen) {
		this.verbindungen = verbindungen;
	}

	public List<HaltestelleDTO> getHalt() {
		return halt;
	}

	public void setHalt(List<HaltestelleDTO> halt) {
		this.halt = halt;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
}
