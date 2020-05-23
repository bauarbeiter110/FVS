package dto;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entities.User;

@Stateless
@LocalBean
public class UserDTO {

	private int id;

	private boolean manager;

	private String name;

	public UserDTO() {
	}
	
	public UserDTO(User us) {
		this.id = us.getId();
		this.manager = us.getManager();
		this.name = us.getName();
	}
	
	public int getId() {
		return this.id;
	}

	public boolean getManager() {
		return this.manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
