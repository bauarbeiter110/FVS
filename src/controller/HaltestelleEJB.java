package controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.*;

import dao.FahrplanDao;
import dao.HaltestelleDao;
import dao.VerbindungDao;
import dto.FahrplanDTO;
import dto.HaltestelleDTO;
import dto.VerbindungDTO;
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
	List<String> weitereHaltestellen;
	
	String name;
	int haltestelleId;

	@PostConstruct
	public void init() {
		haltestellen = halteDao.loadHaltestellen();
	}

	public List<HaltestelleDTO> getHaltestellen() {
		return haltestellen;
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

	public int getHaltestelleId() {
		return haltestelleId;
	}

	public void setHaltestelleId(int haltestelleId) {
		this.haltestelleId = haltestelleId;
	}

	public List<String> getWeitereHaltestellen() {
		return weitereHaltestellen;
	}

	public void setWeitereHaltestellen(List<String> weitereHaltestellen) {
		this.weitereHaltestellen = weitereHaltestellen;
	}
}
