package org.cs.socialmedia.model;

public class FollowUnFollowDTO {
	private Long followerId;
	private Long followeeId;
	
	public FollowUnFollowDTO(Long followerId, Long followeeId) {
		super();
		this.followerId = followerId;
		this.followeeId = followeeId;
	}
	public void setFollowerId(Long followerId) {
		this.followerId = followerId;
	}
	public void setFolloweeId(Long followeeId) {
		this.followeeId = followeeId;
	}
	public Long getFollowerId() {
		return followerId;
	}
	public Long getFolloweeId() {
		return followeeId;
	}
}
