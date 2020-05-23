package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDao;
import dto.UserDTO;

@Named(value = "UserEJB")
@RequestScoped
public class UserEJB {

	@Inject
	UserDao userDao;

	// Variablen auf die von der xhtml zugegriffen wird
	String name;
	int userId;	
	List<UserDTO> users;
	
	// Beim Seitenaufbau immer durchgeführt.
	@PostConstruct
	public void init() {
		users = userDao.loadUsers();
	}

	/** benötigt gebundenen Eingabeparamter #{UserEJB.name}
	 *  Ergänzt einen neuen User in die Datenbank mit "name"
	 */
	public void add() {
		userDao.createUser(this.name);
		users = userDao.loadUsers();
	}

	/** benötigt gebundenen Eingabeparameter #{UserEJB.userID}
	 *  Löscht User mit ausgewählter "userID" aus der Datanbank
	 */
	public void delete() {
		userDao.deleteUser(userId);	
		users = userDao.loadUsers();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<UserDTO> getUsers() {
		return users;
	}
	
	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
