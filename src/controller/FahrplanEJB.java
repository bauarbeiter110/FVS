package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FahrplanDao;
import dao.UserDao;
import dao.VerbindungDao;
import dto.FahrplanDTO;

@Named(value = "FahrplanEJB")
@RequestScoped
public class FahrplanEJB {

	@Inject
	FahrplanDao fahrplanDao;
	
	@Inject
	VerbindungDao verbindungDao;

	// Variablen auf die von der xhtml zugegriffen wird
	String linienname;
	int fahrplanId;	
	List<FahrplanDTO> fahrplaene;
	
	// Beim Seitenaufbau immer durchgeführt.
	@PostConstruct
	public void init() {
		fahrplaene = fahrplanDao.loadFahrplaene();
	}

	public void add() {
		fahrplanDao.createFahrplan(this.linienname);
		fahrplaene = fahrplanDao.loadFahrplaene();
	}

	
	public String getFahrplanById(){
		//Weiterleitung an die Fahrplanübersich einer Haltestelle/ fahrplanId ist in diesem Fall die ID der Haltestelle
		fahrplaene = verbindungDao.getFarhplaeneByHaltestelleId(fahrplanId);	
		return "fahrplanSpeziell.xhtml";
	}

	public String getLinienname() {
		return linienname;
	}

	public void setLinienname(String linienname) {
		this.linienname = linienname;
	}

	public int getFahrplanId() {
		return fahrplanId;
	}

	public void setFahrplanId(int fahrplanId) {
		this.fahrplanId = fahrplanId;
	}

	public List<FahrplanDTO> getFahrplaene() {
		return fahrplaene;
	}

	public void setFahrplaene(List<FahrplanDTO> fahrplaene) {
		this.fahrplaene = fahrplaene;
	}
}
