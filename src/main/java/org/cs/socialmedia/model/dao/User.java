package org.cs.socialmedia.model.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	private String userId;

	public User() {
		super();
	}
}
