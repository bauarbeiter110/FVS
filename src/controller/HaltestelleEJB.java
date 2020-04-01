package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.*;

import dao.HaltestelleDao;
import entities.Haltestelle;

@Named(value = "HaltestelleEJB")
@RequestScoped
public class HaltestelleEJB {

	@Inject
	HaltestelleDao halteDao;

	List<Haltestelle> haltestellen;

	@PostConstruct
	public void init() {
		haltestellen = halteDao.loadUsers();
	}

	public List<Haltestelle> getHaltestellen() {
		return haltestellen;
	}

	public void setHaltestellen(List<Haltestelle> haltestellen) {
		this.haltestellen = haltestellen;
	}

}
