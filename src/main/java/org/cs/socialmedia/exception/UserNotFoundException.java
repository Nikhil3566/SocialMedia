package org.cs.socialmedia.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String id;

	public UserNotFoundException(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}