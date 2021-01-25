package org.cs.socialmedia.service;

import java.util.List;

import org.cs.socialmedia.exception.InvalidFollowRequestException;
import org.cs.socialmedia.exception.InvalidUnfollowRequestException;
import org.cs.socialmedia.exception.UserNotFoundException;
import org.cs.socialmedia.respository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowUnfollowService {

	@Autowired
	FollowRepository followRespository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;

	public String[] follow(String followerId, String followeeId) throws UserNotFoundException,InvalidFollowRequestException {

		userService.validateUser(followerId);
		userService.validateUser(followeeId);

		
		if (!followRespository.isAlreadyFollowing(followerId, followeeId).isEmpty()) {
			throw new InvalidFollowRequestException(followerId, followeeId);
		}
		
		followRespository.follow(followerId, followeeId);
		return getFollowees(followerId);
	}

	public String[] unfollow(String followerId, String followeeId) throws UserNotFoundException,InvalidUnfollowRequestException {

		userService.validateUser(followerId);
		userService.validateUser(followeeId);
		
		if (followRespository.isAlreadyFollowing(followerId, followeeId).isEmpty()) {
			throw new InvalidUnfollowRequestException(followerId, followeeId);
		}

		followRespository.unfollow(followerId, followeeId);

		postService.removefolloweesPostsFromFollowersFeed(followerId, followeeId);

		return getFollowees(followerId);
	}

	public String[] getFollowees(String followerId) {
		return followRespository.getFollowees(followerId);
	}
	
	public List<String> getFollowers(String followeeId) {
		return followRespository.getFollowers(followeeId);
	}
}
