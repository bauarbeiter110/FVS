package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fahrplan_verbindung database table.
 * 
 */
@Entity
@Table(name="fahrplan_verbindung")
@NamedQuery(name="FahrplanVerbindung.findAll", query="SELECT f FROM FahrplanVerbindung f")
public class FahrplanVerbindung implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FahrplanVerbindungPK id;

	private int reinfolge;

	//uni-directional many-to-one association to Fahrplan
	@ManyToOne
	@JoinColumn(name="Fahrplan")
	private Fahrplan Fahrplan;

	//uni-directional many-to-one association to Verbindung
	@ManyToOne
	@JoinColumn(name="Verbindung")
	private Verbindung Verbindung;

	public FahrplanVerbindung() {
	}

	public FahrplanVerbindungPK getId() {
		return this.id;
	}

	public void setId(FahrplanVerbindungPK id) {
		this.id = id;
	}

	public int getReinfolge() {
		return this.reinfolge;
	}

	public void setReinfolge(int reinfolge) {
		this.reinfolge = reinfolge;
	}

	public Fahrplan getFahrplan() {
		return this.Fahrplan;
	}

	public void setFahrplan(Fahrplan Fahrplan) {
		this.Fahrplan = Fahrplan;
	}

	public Verbindung getVerbindung() {
		return this.Verbindung;
	}

	public void setVerbindung(Verbindung Verbindung) {
		this.Verbindung = Verbindung;
	}

}