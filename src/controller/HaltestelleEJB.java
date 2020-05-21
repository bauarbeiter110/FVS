package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.*;

import dao.HaltestelleDao;
import dto.HaltestelleDTO;

@Named(value = "HaltestelleEJB")
@RequestScoped
public class HaltestelleEJB {

	@Inject
	HaltestelleDao halteDao;

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
		halteDao.saveHaltestelle(this.name);
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
