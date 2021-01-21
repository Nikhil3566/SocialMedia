package org.cs.socialmedia.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.cs.socialmedia.constant.SocialMediaConstants;
import org.cs.socialmedia.exception.InvalidUnfollowRequestException;
import org.cs.socialmedia.exception.UserNotFoundException;
import org.cs.socialmedia.model.Post;
import org.cs.socialmedia.model.User;
import org.cs.socialmedia.respository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialMediaService implements ISocialMediaService {

	@Autowired
	UserRespository userRespository;

	public void createPost(Post post) throws UserNotFoundException {
		User loggedInUser = getUserForUserId(post.getUserId());

		// Add post to self's news feed
		loggedInUser.getNewsfeed().push(post);

		// Publish post to all followers' news feed
		Set<User> followers = loggedInUser.getFollowers();

		if (null != followers) {
			followers.forEach(follower -> follower.getNewsfeed().push(post));
		}
	}

	public List<Long> follow(Long followerId, Long followeeId) throws UserNotFoundException {
		User follower = getUserForUserId(followerId);
		User followee = getUserForUserId(followeeId);

		follower.addFollowee(followee);
		followee.addFollower(follower);

		return getFollowees(follower);
	}

	public List<Long> unfollow(Long followerId, Long followeeId)
			throws UserNotFoundException, InvalidUnfollowRequestException {

		User follower = getUserForUserId(followerId);
		User followee = getUserForUserId(followeeId);

		// Removing followee's posts from follower's news feed before un-following him.
		follower.getNewsfeed().removeIf(post -> post.getUserId().equals(followeeId));

		follower.removeFollowee(followee);
		followee.removeFollower(follower);

		return getFollowees(follower);
	}

	public List<Long> getNewsFeed(Long userId) throws UserNotFoundException {
		return getUserForUserId(userId).getNewsfeed().stream().limit(SocialMediaConstants.NEWS_FEED_DISPLAY_LIMIT)
				.map(Post::getPostId).collect(Collectors.toList());
	}

	/**
	 * Fetches a user from data source.
	 * 
	 * @param userId user id of the logged in user.
	 * @return user logged in user object.
	 * @throws UserNotFoundException exception if requested userId is not present in
	 *                               data source.
	 */
	private User getUserForUserId(Long userId) {
		User user = userRespository.getUserForUserId(userId);
		if (null == user) {
			throw new UserNotFoundException(userId);
		}
		return user;
	}

	/**
	 * Fetches a list of followees.
	 * 
	 * @param user logged in user.
	 * @return user list of user ids of followees.
	 */
	private List<Long> getFollowees(User follower) {
		return follower.getFollowees().stream().map(User::getUserId).collect(Collectors.toList());
	}
}
