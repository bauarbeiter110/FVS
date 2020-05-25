package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;

import dto.UserDTO;
import entities.*;

@Stateless
@LocalBean
public class UserDao {
	
	@PersistenceContext(unitName = "fvs")
	private EntityManager em;
	
	/**Lade alle User aus der DB
	 * @return Liste mit UserDTO der user-Tabelle
	 */
	public List<UserDTO> loadUsers() {		
		List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		List<UserDTO> dtos = new ArrayList <UserDTO>();
		users.forEach((user) -> dtos.add(new UserDTO(user)));
		return dtos;		
	}
	
	/**Speichere einen User mit diesem Namen ab. Entweder wird er neu erstellt, oder ein vorhandener abge�ndert
	 * @param name wird verglichen mit Inhalt von User.name
	 */
	public void createUser(String name) {
		em.merge(new User(name));
	}
	
	/**Finde einen User �ber den verwendeten Namen
	 * @param name Name des zu suchenden Users
	 * @return sofern ein User existiert einen entsprechenden UserDTO ansonsten null
	 */
	public UserDTO findUserByName(String name) {
		List <User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		for(User user: users) {
			if(user.getName().equals(name)) {
				return new UserDTO(user);
			}
		}
		return null;
	}
	
	/**L�sche einen User
	 * @param userId PK des zu l�schenden Users
	 */
	public void deleteUser(int userId) {
		em.remove(em.find(User.class, userId));
	}
}
