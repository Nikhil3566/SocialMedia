package org.cs.socialmedia.model.dto;

public class FollowUnFollowDTO {
	private String followerId;
	private String followeeId;

	public FollowUnFollowDTO(String followerId, String followeeId) {
		super();
		this.followerId = followerId;
		this.followeeId = followeeId;
	}

	public String getFollowerId() {
		return followerId;
	}

	public String getFolloweeId() {
		return followeeId;
	}
	
}
