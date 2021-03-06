package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the haltestelle database table.
 * 
 */
@Entity
@Table(name = "haltestelle")
@NamedQuery(name="Haltestelle.findAll", query="SELECT h FROM Haltestelle h")
public class Haltestelle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	public Haltestelle() {
	}
	
	public Haltestelle(String name) {
		this.name = name;
	}

	public Haltestelle(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}