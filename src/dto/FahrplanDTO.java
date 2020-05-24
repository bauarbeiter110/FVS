package dto;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entities.Fahrplan;
import entities.Haltestelle;

@Stateless
@LocalBean
public class FahrplanDTO {

	private int id;

	private String linienname;

	private Time startzeitpunkt;

	private HaltestelleDTO starthaltestelle;

	private HaltestelleDTO zielhaltestelle;

	public FahrplanDTO() {
	}

	public FahrplanDTO(Fahrplan fahr, HaltestelleDTO starthaltestelle, HaltestelleDTO zielhaltestelle) {
		this.id = fahr.getId();
		this.linienname = fahr.getLinienname();
		this.startzeitpunkt = fahr.getStartzeitpunkt();
		this.starthaltestelle = starthaltestelle;
		this.zielhaltestelle = zielhaltestelle;
	}
	
	public FahrplanDTO(int id, String name) {
		this.id = id;
		this.linienname = name;
	}

	public String getLinienname() {
		return linienname;
	}

	public void setLinienname(String linienname) {
		this.linienname = linienname;
	}

	public Time getStartzeitpunkt() {
		return startzeitpunkt;
	}

	public void setStartzeitpunkt(Time startzeitpunkt) {
		this.startzeitpunkt = startzeitpunkt;
	}

	public HaltestelleDTO getStarthaltestelle() {
		return starthaltestelle;
	}

	public void setStarthaltestelle(HaltestelleDTO starthaltestelle) {
		this.starthaltestelle = starthaltestelle;
	}

	public HaltestelleDTO getZielhaltestelle() {
		return zielhaltestelle;
	}

	public void setZielhaltestelle(HaltestelleDTO zielhaltestelle) {
		this.zielhaltestelle = zielhaltestelle;
	}

	public int getId() {
		return id;
	}

	public Fahrplan toEntity() {
		return new Fahrplan(id, linienname, startzeitpunkt, starthaltestelle.toEntity(), zielhaltestelle.toEntity());
	}
}
