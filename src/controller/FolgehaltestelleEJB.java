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
import dao.UserDao;
import dao.VerbindungDao;
import dto.FahrplanDTO;
import dto.HaltestelleDTO;
import dto.VerbindungDTO;

@Named(value = "FolgehaltestelleEJB")
@SessionScoped
public class FolgehaltestelleEJB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7676976521165520848L;

	@Inject
	FahrplanDao fahrplanDao;
	
	@Inject
	VerbindungDao verbindungDao;
	
	int haltestelleId;
	
	List<FahrplanDTO> fahrplaene = new ArrayList<FahrplanDTO>();
	List<HaltestelleDTO> haltestelle = new ArrayList<HaltestelleDTO>();
	
	@PostConstruct
	public void init() {
		
	}
	
	public String toFahrplanSpeziell() {
		fahrplaene = verbindungDao.getFarhplaeneByHaltestelleId(haltestelleId);
		return "fahrplanSpeziell.xhtml";
	}
	
	public String toFolgehaltestelle(int linienId) {
		haltestelle = verbindungDao.getSortedHaltestellenByFahrplanId(linienId);
		for(int i = 0; i<haltestelle.size(); i++) {
			if(haltestelle.get(i).getId() == haltestelleId) {
				//Dies ist die Haltestelle nach der alle Haltestellen angezeigt werden sollen.
				//Abbruchbedingung
				i = haltestelle.size();
			}
			else {
				haltestelle.remove(i);
				i--;
			}
		}
		return "folgehaltestellen.xhtml";
	}

	public int getHaltestelleId() {
		return haltestelleId;
	}

	public void setHaltestelleId(int haltestelleId) {
		this.haltestelleId = haltestelleId;
	}

	public List<FahrplanDTO> getFahrplaene() {
		return fahrplaene;
	}

	public void setFahrplaene(List<FahrplanDTO> fahrplaene) {
		this.fahrplaene = fahrplaene;
	}

	public List<HaltestelleDTO> getHaltestelle() {
		return haltestelle;
	}

	public void setHaltestelle(List<HaltestelleDTO> haltestelle) {
		this.haltestelle = haltestelle;
	}

	
}
