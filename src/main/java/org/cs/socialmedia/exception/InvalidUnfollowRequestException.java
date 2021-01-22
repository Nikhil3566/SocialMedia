package org.cs.socialmedia.exception;

public class InvalidUnfollowRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Long followerId;
	private final Long followeeId;

	public InvalidUnfollowRequestException(Long followerId, Long followeeId) {
		super();
		this.followerId = followerId;
		this.followeeId = followeeId;
	}

	public Long getFollowerId() {
		return followerId;
	}

	public Long getFolloweeId() {
		return followeeId;
	}

}