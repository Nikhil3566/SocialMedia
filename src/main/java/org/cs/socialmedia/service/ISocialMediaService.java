package org.cs.socialmedia.service;

import java.util.List;

import org.cs.socialmedia.exception.InvalidUnfollowRequestException;
import org.cs.socialmedia.exception.UserNotFoundException;
import org.cs.socialmedia.model.Post;

public interface ISocialMediaService {

	/**
	 * Creates a post by a certain user.
	 * 
	 * @param post post contains userId, postId and post content.
	 * @return void
	 * @throws UserNotFoundException if user is not found
	 */
	public void createPost(Post post) throws UserNotFoundException;

	/**
	 * Allows a user to follow another user.
	 * 
	 * @param followerId userId of follower user.
	 * @param followeeId userId of followed user.
	 * @return list of followees' ids.
	 * @throws UserNotFoundException if follower or followee is not found
	 */
	public List<Long> follow(Long followerId, Long followeeId) throws UserNotFoundException;

	/**
	 * Allows a user to unfollow another user.
	 * 
	 * @param followerId userId of follower user.
	 * @param followeeId userId of followed user.
	 * @return list of followees' ids.
	 * @throws UserNotFoundException           if follower or followee is not found,
	 * @throws InvalidUnfollowRequestException if follower is not already following
	 *                                         the followee
	 */
	public List<Long> unfollow(Long followerId, Long followeeId)
			throws UserNotFoundException, InvalidUnfollowRequestException;

	/**
	 * Fetches 20 most recent post ids of posts by a certain user as well as all her
	 * followed users.
	 * 
	 * @param userId userId of the logged in user.
	 * @return List<Long> list 20 most recent post ids of posts by a certain user as
	 *         well as all her followed users.
	 * @throws UserNotFoundException if user is not found
	 */
	public List<Long> getNewsFeed(Long userId) throws UserNotFoundException;

}
