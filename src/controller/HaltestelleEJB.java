package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.*;

import dao.HaltestelleDao;
import entities.Haltestelle;
import entities.User;

@Named(value = "HaltestelleEJB")
@RequestScoped
public class HaltestelleEJB {

	@Inject
	HaltestelleDao halteDao;

	List<Haltestelle> haltestellen;
	
	String name;
	int haltestelleId;

	@PostConstruct
	public void init() {
		haltestellen = halteDao.loadHaltestellen();
	}

	public List<Haltestelle> getHaltestellen() {
		return haltestellen;
	}

	public void setHaltestellen(List<Haltestelle> haltestellen) {
		this.haltestellen = haltestellen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void add() {
		System.out.println("name: " + this.name);
		Haltestelle newHaltestelle = new Haltestelle(this.name);
		System.out.println("Haltestelle: " + newHaltestelle.toString());
		halteDao.saveHaltestelle(newHaltestelle);
		haltestellen = halteDao.loadHaltestellen();
	}
	
	public void delete() {
		System.out.println("Bind: " + this.haltestelleId);
		for (Haltestelle halte : haltestellen) {
			if(halte.getId()==this.haltestelleId) {
				System.out.println(halte.toString());
				halteDao.deleteHaltestelle(haltestelleId);	
				haltestellen = halteDao.loadHaltestellen();
			}
		}
	}

	public int getHaltestelleId() {
		return haltestelleId;
	}

	public void setHaltestelleId(int haltestelleId) {
		this.haltestelleId = haltestelleId;
	}
}
