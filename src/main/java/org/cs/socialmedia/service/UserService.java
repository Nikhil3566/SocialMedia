package org.cs.socialmedia.service;

import org.cs.socialmedia.exception.UserNotFoundException;
import org.cs.socialmedia.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public void validateUser(String userId) throws UserNotFoundException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new UserNotFoundException(userId);
		}
	}
}
