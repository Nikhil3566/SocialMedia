package org.cs.socialmedia.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "followdetails") 
public class FollowDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "follower_id")
	private String followerId;

	@Column(name = "followee_id")
	private String followeeId;

}
