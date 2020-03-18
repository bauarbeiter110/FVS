package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.List;


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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private Time dauer;

	//bi-directional many-to-many association to Fahrplan
	@ManyToMany(mappedBy="verbindungen")
	private List<Fahrplan> fahrplaene;

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

	public List<Fahrplan> getFahrplaene() {
		return this.fahrplaene;
	}

	public void setFahrplaene(List<Fahrplan> fahrplaene) {
		this.fahrplaene = fahrplaene;
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