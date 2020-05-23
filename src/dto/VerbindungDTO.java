package dto;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import entities.Haltestelle;
import entities.Verbindung;

public class VerbindungDTO {

	private int id;

	private Time dauer;

	private HaltestelleDTO Ursprung;

	private HaltestelleDTO Ziel;

	public VerbindungDTO() {
	}

	public VerbindungDTO(Time dauer, HaltestelleDTO ursprung, HaltestelleDTO ziel) {
		this.dauer = dauer;
		this.Ursprung = ursprung;
		this.Ziel = ziel;
	}

	public VerbindungDTO(Verbindung ver) {
		this.id = ver.getId();
		this.dauer = ver.getDauer();
		this.Ursprung = new HaltestelleDTO(ver.getUrsprung());
		this.Ziel = new HaltestelleDTO(ver.getZiel());
	}

	public int getId() {
		return this.id;
	}

	public Time getDauer() {
		return this.dauer;
	}

	public void setDauer(Time dauer) {
		this.dauer = dauer;
	}

	public HaltestelleDTO getUrsprung() {
		return this.Ursprung;
	}

	public void setUrsprung(HaltestelleDTO Ursprung) {
		this.Ursprung = Ursprung;
	}

	public HaltestelleDTO getZiel() {
		return this.Ziel;
	}

	public void setZiel(HaltestelleDTO Ziel) {
		this.Ziel = Ziel;
	}

	public Verbindung toEntity() {
		return new Verbindung(Ursprung.toEntity(), Ziel.toEntity(), dauer);
	}

}
