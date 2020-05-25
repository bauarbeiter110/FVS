package controller;

import java.io.Serializable;
import java.sql.Time;
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
	List<VerbindungDTO> haltestellen;
	FahrplanDTO fahr;
	String linienname;

	public String fahrplanUebersichtToHaltestelleSpeziell() {
		fahr = fahrDao.getFahrplanById(linieId);
		linienname = fahr.getLinienname();
		HaltestelleDTO lastHalt = fahr.getZielhaltestelle();
		lastHaltId = lastHalt.getId();
		mglNextHalt = new ArrayList<HaltestelleDTO>();
		// Ermittlung der möglichen nächsten Verbindungen
		List<VerbindungDTO> verbindungen = verbindungDao.loadVerbindung();
		
		List<HaltestelleDTO> haltestelle = verbindungDao.getSortedHaltestellenByFahrplanId(linieId);
		List<Time> times = verbindungDao.getSortedTimeByFahrplanId(linieId);
		haltestellen = new ArrayList<VerbindungDTO>();
		haltestellen.add(new VerbindungDTO(fahr.getStartzeitpunkt(), fahr.getStarthaltestelle(), fahr.getStarthaltestelle()));
		for(int i = 0 ; i <= times.size()-1; i++) {
			@SuppressWarnings("deprecation")
			int hh = haltestellen.get(i).getDauer().getHours() + times.get(i).getHours();
			int mm = haltestellen.get(i).getDauer().getMinutes() + times.get(i).getMinutes();
			Time t = new Time(hh , mm , 0);
			haltestellen.add(new VerbindungDTO(t, haltestelle.get(i+1), haltestelle.get(i+1)));
		}
		
		// Ermittlung der Vorletzten Haltestelle.
		VerbindungDTO lastVer = verbindungDao.getlastVerbindung(linieId);
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
		if (ver.getZiel().getId() == fahr.getZielhaltestelle().getId()) {
			fahr.setZielhaltestelle(ver.getUrsprung());
		} else {
			fahr.setZielhaltestelle(ver.getZiel());
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

	public List<VerbindungDTO> getHaltestellen() {
		return haltestellen;
	}

	public void setHaltestellen(List<VerbindungDTO> haltestellen) {
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
