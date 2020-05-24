package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FahrplanDao;
import dao.FahrplanVerbindungDao;
import dao.HaltestelleDao;
import dao.VerbindungDao;
import dto.FahrplanDTO;
import dto.FahrplanVerbindungDTO;
import dto.HaltestelleDTO;
import dto.VerbindungDTO;
import entities.FahrplanVerbindung;

@Named(value = "FahrplanVerbindungEJB")
@SessionScoped
public class FahrplanVerbindungEJB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7105629845022662576L;

	@Inject
	HaltestelleDao haltDao;

	@Inject
	FahrplanDao fahrDao;

	@Inject
	VerbindungDao verbindungDao;

	@Inject
	FahrplanVerbindungDao fahrVerDao;

	int lastHaltId;
	int nextVerId;
	int linieId;
	List<VerbindungDTO> verbindungen;
	List<HaltestelleDTO> haltestellen;

	public String fahrplanUebersichtToHaltestelleSpeziell() {
		haltestellen = verbindungDao.getHaltestellenByFahrplanId(linieId);
		lastHaltId = haltestellen.get(haltestellen.size() - 1).getId();

		// Ermittlung der möglichen nächsten Verbindungen
		verbindungen = verbindungDao.loadVerbindung();
		List<VerbindungDTO> zielverbindungen = new ArrayList<VerbindungDTO>();
		for (int i = 0; i < verbindungen.size(); i++) {
			if (verbindungen.get(i).getUrsprung().getId() == lastHaltId) {
				zielverbindungen.add(verbindungen.get(i));
			}
		}
		verbindungen = zielverbindungen;
		return "haltestelleSpeziell.xhtml";
	}

	public String addVerbindung() {
		FahrplanDTO fahr = fahrDao.getFahrplanById(linieId);
		FahrplanVerbindungDTO fahrVer = new FahrplanVerbindungDTO(
				verbindungDao.getHaltestellenByFahrplanId(linieId).size(), fahr,
				verbindungDao.getVerbindungById(nextVerId));
		fahrVerDao.saveFahrplanVerbindung(fahrVer);
		// Wenn die neue Verbindung gespeichert ist, muss für den Fahrplan noch die
		// Zielhaltestelle aktualisiert werden.
		if (!verbindungDao.getVerbindungById(nextVerId).getZiel().equals(fahr.getZielhaltestelle())) {
			System.out.println(verbindungDao.getVerbindungById(nextVerId).getZiel().getName());
			fahr.setZielhaltestelle(verbindungDao.getVerbindungById(nextVerId).getZiel());
		} else {
			fahr.setZielhaltestelle(verbindungDao.getVerbindungById(nextVerId).getUrsprung());
			System.out.println(verbindungDao.getVerbindungById(nextVerId).getUrsprung().getName());
		}
		fahrDao.saveFahrplan(fahr);
		return "navigation.xhtml";
	}

	public String zurueck() {
		return "haltestelleUebersicht.xhtml";
	}

	public int getLastHaltId() {
		return lastHaltId;
	}

	public void setLastHaltId(int lastHaltId) {
		this.lastHaltId = lastHaltId;
	}

	public int getNextVerId() {
		return nextVerId;
	}

	public void setNextVerId(int nextVer) {
		this.nextVerId = nextVer;
	}

	public List<VerbindungDTO> getVerbindungen() {
		return verbindungen;
	}

	public void setVerbindungen(List<VerbindungDTO> verbindungen) {
		this.verbindungen = verbindungen;
	}

	public int getLinieId() {
		return linieId;
	}

	public void setLinieId(int linieId) {
		this.linieId = linieId;
	}

	public List<HaltestelleDTO> getHaltestellen() {
		return haltestellen;
	}

	public void setHaltestellen(List<HaltestelleDTO> haltestellen) {
		this.haltestellen = haltestellen;
	}
}
