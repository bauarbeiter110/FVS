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
	List<UserDTO> deleteUsers;
	
	// Beim Seitenaufbau immer durchgeführt.
	@PostConstruct
	public void init() {
		users = userDao.loadUsers();
		deleteUsers = userDao.loadUsers();
		for(int i = 0; i<deleteUsers.size();i++) {
			if(deleteUsers.get(i).getManager()) {
				deleteUsers.remove(i);
				i--;
			}
		}
	}

	/** benötigt gebundenen Eingabeparamter #{UserEJB.name}
	 *  Ergänzt einen neuen User in die Datenbank mit "name"
	 */
	public void add() {
		userDao.createUser(this.name);
		init();
	}

	/** benötigt gebundenen Eingabeparameter #{UserEJB.userID}
	 *  Löscht User mit ausgewählter "userID" aus der Datanbank
	 */
	public void delete() {
		userDao.deleteUser(userId);	
		init();
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

	public List<UserDTO> getDeleteUsers() {
		return deleteUsers;
	}

	public void setDeleteUsers(List<UserDTO> deleteUsers) {
		this.deleteUsers = deleteUsers;
	}
}
