package dto;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class HaltestelleDTO {
	
	private int id;

	private String name;

	public HaltestelleDTO() {
	}
	
	public HaltestelleDTO(String name) {
		this.name = name;
	}
	
	public HaltestelleDTO(int id, String name) {
		this.id = id;
		this.name = name;
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
