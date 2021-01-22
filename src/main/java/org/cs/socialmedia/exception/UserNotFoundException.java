package org.cs.socialmedia.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Long id;

	public UserNotFoundException(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}