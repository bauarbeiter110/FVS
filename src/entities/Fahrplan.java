package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the fahrplan database table.
 * 
 */
@Entity
@Table(name = "fahrplan")
@NamedQuery(name="Fahrplan.findAll", query="SELECT f FROM Fahrplan f")
public class Fahrplan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String linienname;

	private Time startzeitpunkt;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="Starthaltestelle")
	private Haltestelle Starthaltestelle;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="Zielhaltestelle")
	private Haltestelle Zielhaltestelle;

	public Fahrplan() {
	}

	public Fahrplan(String name) {
		this.linienname = name;
	}
	
	public Fahrplan(int id, String linienname, Time startzeitpunkt, Haltestelle starthalt, Haltestelle zielhalt) {
		this.id = id;
		this.linienname = linienname;
		this.startzeitpunkt = startzeitpunkt;
		this.Starthaltestelle = starthalt;
		this.Zielhaltestelle = zielhalt;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLinienname() {
		return this.linienname;
	}

	public void setLinienname(String linienname) {
		this.linienname = linienname;
	}

	public Time getStartzeitpunkt() {
		return this.startzeitpunkt;
	}

	public void setStartzeitpunkt(Time startzeitpunkt) {
		this.startzeitpunkt = startzeitpunkt;
	}

	public Haltestelle getStarthaltestelle() {
		return this.Starthaltestelle;
	}

	public void setStarthaltestelle(Haltestelle Starthaltestelle) {
		this.Starthaltestelle = Starthaltestelle;
	}

	public Haltestelle getZielhaltestelle() {
		return this.Zielhaltestelle;
	}

	public void setZielhaltestelle(Haltestelle Zielhaltestelle) {
		this.Zielhaltestelle = Zielhaltestelle;
	}

}