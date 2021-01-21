package org.cs.socialmedia.model;

import java.util.HashSet;
import java.util.Set;

import org.cs.socialmedia.exception.InvalidUnfollowRequestException;

public class User {

	private Long userId;
	private Set<User> followers;
	private Set<User> followees;
	private Newsfeed newsfeed;

	public User(Long userId) {
		super();
		this.userId = userId;
		this.newsfeed = new Newsfeed();
	}

	public Long getUserId() {
		return userId;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void addFollower(User follower) {
		if (null == followers) {
			followers = new HashSet<>();
		}
		followers.add(follower);
	}

	public void removeFollower(User follower) {
		if (null == followers || followers.isEmpty() || !followers.contains(follower)) {
			throw new InvalidUnfollowRequestException(follower.userId, this.userId);
		} else {
			followers.remove(follower);
		}
	}

	public Set<User> getFollowees() {
		return followees;
	}

	public void addFollowee(User followee) {
		if (null == followees) {
			followees = new HashSet<>();
		}
		followees.add(followee);
	}

	public void removeFollowee(User followee) {
		if (null == followees || followees.isEmpty() || !followees.contains(followee)) {
			throw new InvalidUnfollowRequestException(this.userId, followee.userId);
		} else {
			followees.remove(followee);
		}
	}

	public Newsfeed getNewsfeed() {
		return newsfeed;
	}
}
