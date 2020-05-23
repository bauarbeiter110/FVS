package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.*;

import dao.FahrplanDao;
import dao.HaltestelleDao;
import dao.VerbindungDao;
import dto.FahrplanDTO;
import dto.HaltestelleDTO;
import entities.Haltestelle;

@Named(value = "HaltestelleEJB")
@RequestScoped
public class HaltestelleEJB {

	@Inject
	HaltestelleDao halteDao;
	
	@Inject
	FahrplanDao fahrplanDao;
	
	@Inject
	VerbindungDao verbindungDao;

	List<HaltestelleDTO> haltestellen;
	
	String name;
	int haltestelleId;

	@PostConstruct
	public void init() {
		haltestellen = halteDao.loadHaltestellen();
	}

	public List<HaltestelleDTO> getHaltestellen() {
		return haltestellen;
	}
	
	public String getHaltestellenById(){
		//Weiterleitung an die Haltestellenübersicht einer Linie/ haltestelleId ist in diesem Fall die ID der Fahrplanes
		System.out.println("FahrplanId " + haltestelleId);
		haltestellen = verbindungDao.getHaltestellenByFahrplanId(haltestelleId);	
		return "haltestelle.xhtml";
	}

	public void setHaltestellen(List<HaltestelleDTO> haltestellen) {
		this.haltestellen = haltestellen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void add() {
		halteDao.createHaltestelle(this.name);
		haltestellen = halteDao.loadHaltestellen();
	}
	
	public void delete() {
		halteDao.deleteHaltestelle(haltestelleId);	
		haltestellen = halteDao.loadHaltestellen();
	}

	public int getHaltestelleId() {
		return haltestelleId;
	}

	public void setHaltestelleId(int haltestelleId) {
		this.haltestelleId = haltestelleId;
	}
}
