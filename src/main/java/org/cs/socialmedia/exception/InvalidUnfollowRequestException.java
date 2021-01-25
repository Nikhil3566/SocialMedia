package org.cs.socialmedia.exception;

public class InvalidUnfollowRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String followerId;
	private final String followeeId;

	public InvalidUnfollowRequestException(String followerId, String followeeId) {
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