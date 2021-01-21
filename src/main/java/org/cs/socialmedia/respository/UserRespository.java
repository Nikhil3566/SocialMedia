package org.cs.socialmedia.respository;

import java.util.HashMap;
import java.util.Map;

import org.cs.socialmedia.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRespository {
	
	public static Map<Long,User> users ;
	static {
		users = new HashMap<>();
		users.put(1L,new User(1L));
		users.put(2L,new User(2L));
		users.put(3L,new User(3L));
		users.put(4L,new User(4L));
		users.put(5L,new User(5L));
	}
	
	/* Saves a user in dummy data source static Hashmap
	 * * */
	public void saveUser(Long userId, User user) {
		this.users.put(userId, user);
	}
	
	/* Retrieves a user from dummy data source static Hashmap
	 * * */
	public User getUserForUserId(Long userId) {
		return this.users.get(userId);
	}
}
