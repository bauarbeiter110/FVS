package controller;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FahrplanDao;
import dao.FahrplanVerbindungDao;
import dao.VerbindungDao;
import dto.FahrplanDTO;
import dto.FahrplanVerbindungDTO;
import dto.VerbindungDTO;

@Named(value = "StarthaltestelleEJB")
@SessionScoped
public class StarthaltestelleEJB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4549572581118167572L;

	@Inject
	FahrplanDao fahrplanDao;
	
	@Inject
	VerbindungDao verbindungDao;
	
	@Inject
	FahrplanVerbindungDao fahrVerDao;

	// Variablen auf die von der xhtml zugegriffen wird
	String fahrplanname;
	int fahrplanId;
	int nextVerbId;
	List<VerbindungDTO> verbindungen;
	int min;
	int hh;
	FahrplanDTO fahr;
	
	// Beim Seitenaufbau immer durchgeführt.
	@PostConstruct
	public void init() {
		//Fahrpläne enthalten alle Fahrpläne
		verbindungen = verbindungDao.loadVerbindung();			
	}
	
	public String fahrplanUebersichtToStarthaltstelle() {
		fahr = fahrplanDao.getFahrplanById(fahrplanId);
		fahrplanname = fahr.getLinienname();
		return "starthaltestelle.xhtml";
	}
	
	public String addStartverbindung(){
		@SuppressWarnings("deprecation")
		Time t = new Time(hh, min, 0);
		
		fahr.setStartzeitpunkt(t);
		VerbindungDTO verb = verbindungDao.findVerbindungById(nextVerbId);
		fahr.setStarthaltestelle(verb.getUrsprung());
		fahr.setZielhaltestelle(verb.getZiel());
		fahrplanDao.saveFahrplan(fahr);
		fahrVerDao.saveFahrplanVerbindung(new FahrplanVerbindungDTO(1, fahr, verb));
		return "navigation.xhtml";
	}
	
	public int getFahrplanId() {
		return fahrplanId;
	}

	public void setFahrplanId(int fahrplanId) {
		this.fahrplanId = fahrplanId;
	}

	public List<VerbindungDTO> getVerbindungen() {
		return verbindungen;
	}

	public void setVerbindungen(List<VerbindungDTO> verbindungen) {
		this.verbindungen = verbindungen;
	}

	public int getNextVerbId() {
		return nextVerbId;
	}

	public void setNextVerbId(int nextVerbId) {
		this.nextVerbId = nextVerbId;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getHh() {
		return hh;
	}

	public void setHh(int hh) {
		this.hh = hh;
	}

	public String getFahrplanname() {
		return fahrplanname;
	}

	public void setFahrplanname(String fahrplanname) {
		this.fahrplanname = fahrplanname;
	}
}
