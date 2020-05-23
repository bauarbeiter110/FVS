package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fahrplan_verbindung database table.
 * 
 */
@Embeddable
public class FahrplanVerbindungPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int fahrplan;

	@Column(insertable=false, updatable=false)
	private int verbindung;

	public FahrplanVerbindungPK() {
	}
	public int getFahrplan() {
		return this.fahrplan;
	}
	public void setFahrplan(int fahrplan) {
		this.fahrplan = fahrplan;
	}
	public int getVerbindung() {
		return this.verbindung;
	}
	public void setVerbindung(int verbindung) {
		this.verbindung = verbindung;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FahrplanVerbindungPK)) {
			return false;
		}
		FahrplanVerbindungPK castOther = (FahrplanVerbindungPK)other;
		return 
			(this.fahrplan == castOther.fahrplan)
			&& (this.verbindung == castOther.verbindung);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fahrplan;
		hash = hash * prime + this.verbindung;
		
		return hash;
	}
}