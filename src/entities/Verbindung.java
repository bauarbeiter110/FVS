package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the verbindung database table.
 * 
 */
@Entity
@Table(name = "verbindung")
@NamedQuery(name="Verbindung.findAll", query="SELECT v FROM Verbindung v")
public class Verbindung implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private Time dauer;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="Ursprung")
	private Haltestelle Ursprung;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="Ziel")
	private Haltestelle Ziel;

	public Verbindung() {
	}

	public Verbindung(int id, Haltestelle ursprung, Haltestelle ziel, Time dauer) {
		this.Ursprung = ursprung;
		this.Ziel = ziel;
		this.dauer = dauer;
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getDauer() {
		return this.dauer;
	}

	public void setDauer(Time dauer) {
		this.dauer = dauer;
	}

	public Haltestelle getUrsprung() {
		return this.Ursprung;
	}

	public void setUrsprung(Haltestelle Ursprung) {
		this.Ursprung = Ursprung;
	}

	public Haltestelle getZiel() {
		return this.Ziel;
	}

	public void setZiel(Haltestelle Ziel) {
		this.Ziel = Ziel;
	}

}