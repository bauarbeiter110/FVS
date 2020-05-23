package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FahrplanDao;
import dao.UserDao;
import dto.FahrplanDTO;

@Named(value = "FahrplanEJB")
@RequestScoped
public class FahrplanEJB {

	@Inject
	FahrplanDao fahrplanDao;

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

	public void delete() {
		fahrplanDao.deleteFahrplan(fahrplanId);	
		fahrplaene = fahrplanDao.loadFahrplaene();
	}
	
	public void loadHaltestellen() {
		System.out.println(fahrplanId);
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
