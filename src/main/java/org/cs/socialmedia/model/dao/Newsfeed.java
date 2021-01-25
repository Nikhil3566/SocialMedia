package org.cs.socialmedia.model.dao;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "newsfeed")
public class Newsfeed {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "post_id")
	private String postId;

	@Column(name = "post_originator_id")
	private String postOriginatorId;

	@Column(name = "newsfeed_owner_id")
	private String newsfeedOwnerId;

	@Column(name = "content")
	private String content;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	public Newsfeed(String postId, String postOriginatorId, String newsfeedOwnerId, String content,
			LocalDateTime creationDate) {
		super();
		this.postId = postId;
		this.postOriginatorId = postOriginatorId;
		this.newsfeedOwnerId = newsfeedOwnerId;
		this.content = content;
		this.creationDate = creationDate;
	}

	public int getId() {
		return id;
	}
}
