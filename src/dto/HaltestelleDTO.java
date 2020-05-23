package dto;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entities.Haltestelle;

@Stateless
@LocalBean
public class HaltestelleDTO {
	
	private int id;

	private String name;

	public HaltestelleDTO() {
	}
	
	public HaltestelleDTO(Haltestelle halt) {
		this.id = halt.getId();
		this.name = halt.getName();
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
