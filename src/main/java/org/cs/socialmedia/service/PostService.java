package org.cs.socialmedia.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.cs.socialmedia.exception.UserNotFoundException;
import org.cs.socialmedia.model.dao.Newsfeed;
import org.cs.socialmedia.model.dto.PostDTO;
import org.cs.socialmedia.respository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserService userService;

	@Autowired
	FollowUnfollowService followUnfollowService;

	public void createPost(PostDTO post) throws UserNotFoundException {

		userService.validateUser(post.getUserId());

		LocalDateTime postCreationTime = LocalDateTime.now();

		// Add post to self's news feed
		postRepository.save(new Newsfeed(post.getPostId(), post.getUserId(), post.getUserId(), post.getContent(),
				postCreationTime));

		// Publish post to all followers' news feed
		List<String> followerIdsList = followUnfollowService.getFollowers(post.getUserId());

		List<Newsfeed> newsfeedForFollowers;
		
		if (!followerIdsList.isEmpty()) {
			newsfeedForFollowers = new LinkedList<>();

			for (String followerId : followerIdsList) {
				newsfeedForFollowers.add(new Newsfeed(post.getPostId(), post.getUserId(), followerId, post.getContent(),
						postCreationTime));
				postRepository.saveAll(newsfeedForFollowers);
			}
		}
	}

	public String[] getNewsFeed(String userId) throws UserNotFoundException {

		userService.validateUser(userId);

		return postRepository.get20RecentNewsfeed(userId);
	}

	public void removefolloweesPostsFromFollowersFeed(String followerId, String followeeId) {
		postRepository.removefolloweesPostsFromFollowersFeed(followerId, followeeId);
	}
}
