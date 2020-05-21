package dao;

import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.metamodel.EntityType;

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
		List<UserDTO> users = em.createQuery("SELECT u FROM User u", UserDTO.class).getResultList();
		return users;		
	}
	
	/**Speichere einen User mit diesem Namen ab. Entweder wird er neu erstellt, oder ein vorhandener abgeändert
	 * @param name wird verglichen mit Inhalt von User.name
	 */
	public void saveUser(String name) {
		List <User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		boolean changeUser = false;
		for(User user: users) {
			if(user.getName().equals(name)) {		
				em.merge(user);
				changeUser = true;
			}
		}
	// Der User wurde nicht im Persistenz-Kontext gefunden. Es ist ein neuer User
		if (changeUser == false) {
			em.merge(new User(name));
		}	
	}
	
	public void deleteUser(int userId) {
		em.remove(em.find(User.class, userId));
	}
	
	public UserDTO findUserByName(String name) {
		List <User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		for(User user: users) {
			if(user.getName().equals(name)) {
				return new UserDTO(user.getId(), user.getManager(), user.getName());
			}
		}
		return null;
	}
	
	public void gibMetaAus() {
		Set<EntityType<?>> tabellen = em.getMetamodel().getEntities();
    	for(EntityType tabelle : tabellen) {
    		System.out.println(tabelle.toString());
    	}
	}
}
