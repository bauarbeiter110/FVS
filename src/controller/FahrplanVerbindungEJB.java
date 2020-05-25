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
	int nextHaltId;
	int linieId;
	List<HaltestelleDTO> mglNextHalt;
	List<HaltestelleDTO> haltestellen;
	FahrplanDTO fahr;
	String linienname;

	public String fahrplanUebersichtToHaltestelleSpeziell() {
		haltestellen = verbindungDao.getSortedHaltestellenByFahrplanId(linieId);
		fahr = fahrDao.getFahrplanById(linieId);
		linienname = fahr.getLinienname();
		HaltestelleDTO lastHalt = fahr.getZielhaltestelle();
		lastHaltId = lastHalt.getId();
		mglNextHalt = new ArrayList<HaltestelleDTO>();
		// Ermittlung der möglichen nächsten Verbindungen
		List<VerbindungDTO> verbindungen = verbindungDao.loadVerbindung();

		// Ermittlung der Vorletzten Haltestelle.
		VerbindungDTO lastVer = fahrDao.getlastVerbindung(linieId);
		for (int i = 0; i < verbindungen.size(); i++) {
			VerbindungDTO ver = verbindungen.get(i);
			if (ver.getId() == lastVer.getId()) {
				// Die betrachtete Verbindung ist die letzte Verbindung--> Nichts tun
			} else {
				// Die jeweilige mögliche nächste Haltestelle ist immer der Teil der
				// betrachteten Verbindung die nicht die letzte Haltestelle ist.
				if (ver.getUrsprung().getId() == lastHaltId) {
					mglNextHalt.add(ver.getZiel());
				} else if (ver.getZiel().getId() == lastHaltId) {
					mglNextHalt.add(ver.getUrsprung());
				}
			}
		}
		return "haltestelleSpeziell.xhtml";
	}

	public String fahrplanUebersichtToStarthaltestelle() {
		return "starthaltestelle.xhtml";
	}

	public String addVerbindung() {
		VerbindungDTO ver = verbindungDao.getVerbindungByHaltestellen(haltDao.findHaltestelleById(lastHaltId),
				haltDao.findHaltestelleById(nextHaltId));
		FahrplanVerbindungDTO fahrVer = new FahrplanVerbindungDTO(
				verbindungDao.getSortedHaltestellenByFahrplanId(linieId).size(), fahr, ver);
		fahrVerDao.saveFahrplanVerbindung(fahrVer);
		// Wenn die neue Verbindung gespeichert ist, muss für den Fahrplan noch die
		// Zielhaltestelle aktualisiert werden.
		if (ver.getZiel().getId() != fahr.getZielhaltestelle().getId()) {
			fahr.setZielhaltestelle(ver.getZiel());
		} else {
			fahr.setZielhaltestelle(ver.getUrsprung());
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

	public int getNextHaltId() {
		return nextHaltId;
	}

	public void setNextHaltId(int nextVer) {
		this.nextHaltId = nextVer;
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

	public List<HaltestelleDTO> getMglNextHalt() {
		return mglNextHalt;
	}

	public void setMglNextHalt(List<HaltestelleDTO> mglNextHalt) {
		this.mglNextHalt = mglNextHalt;
	}

	public String getLinienname() {
		return linienname;
	}

	public void setLinienname(String linienname) {
		this.linienname = linienname;
	}
}
