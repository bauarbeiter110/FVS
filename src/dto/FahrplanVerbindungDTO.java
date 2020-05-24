package dto;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;

import entities.*;


@Stateless
@LocalBean
public class FahrplanVerbindungDTO {

	private int reinfolge;

	private FahrplanDTO Fahrplan;

	private VerbindungDTO Verbindung;

	public FahrplanVerbindungDTO() {
	}
	
	public FahrplanVerbindungDTO(int reinfolge, FahrplanDTO fahr, VerbindungDTO ver) {
		this.reinfolge = reinfolge;
		this.Fahrplan = fahr;
		this.Verbindung = ver;
	}

	public int getReinfolge() {
		return this.reinfolge;
	}

	public void setReinfolge(int reinfolge) {
		this.reinfolge = reinfolge;
	}

	public FahrplanDTO getFahrplan() {
		return this.Fahrplan;
	}

	public void setFahrplan(FahrplanDTO Fahrplan) {
		this.Fahrplan = Fahrplan;
	}

	public VerbindungDTO getVerbindung() {
		return this.Verbindung;
	}

	public void setVerbindung(VerbindungDTO Verbindung) {
		this.Verbindung = Verbindung;
	}

	public FahrplanVerbindung toEntity() {
		return new FahrplanVerbindung(reinfolge, Fahrplan.toEntity(), Verbindung.toEntity());
	}

}