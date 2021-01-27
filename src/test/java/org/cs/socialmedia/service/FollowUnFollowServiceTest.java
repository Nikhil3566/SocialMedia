package org.cs.socialmedia.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.cs.socialmedia.exception.InvalidFollowRequestException;
import org.cs.socialmedia.exception.InvalidUnfollowRequestException;
import org.cs.socialmedia.exception.UserNotFoundException;
import org.cs.socialmedia.respository.FollowRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class FollowUnFollowServiceTest {

	@InjectMocks
	private FollowUnfollowService followUnfollowService;

	@Mock
	FollowRepository followRespository;

	@Mock
	UserService userService;

	@Mock
	PostService postService;

	private String followerUserId = "followerUserId";
	private String followeeUserId = "followeeUserId";

	@Test
	public void testFollowSuccess() {
		Mockito.doNothing().when(userService).validateUser(followerUserId);
		Mockito.doNothing().when(userService).validateUser(followeeUserId);
		Mockito.when(followRespository.isAlreadyFollowing(followerUserId, followeeUserId))
				.thenReturn(new LinkedList<>());
		Mockito.doNothing().when(followRespository).follow(followerUserId, followeeUserId);

		String[] expectedFollowees = new String[] { followeeUserId };

		Mockito.when(followRespository.getFollowees(followerUserId)).thenReturn(expectedFollowees);

		String[] actualFollowees = followUnfollowService.follow(followerUserId, followeeUserId);

		assertArrayEquals(expectedFollowees, actualFollowees);
	}

	@Test(expected = InvalidFollowRequestException.class)
	public void testAlreadyFollowing() {
		Mockito.doNothing().when(userService).validateUser(followerUserId);
		Mockito.doNothing().when(userService).validateUser(followeeUserId);

		List<String> followerList = new ArrayList<>();
		followerList.add(followerUserId);

		Mockito.when(followRespository.isAlreadyFollowing(followerUserId, followeeUserId)).thenReturn(followerList);
		followUnfollowService.follow(followerUserId, followeeUserId);
	}

	@Test(expected = UserNotFoundException.class)
	public void testFollowMethodWhereFollowerIsInvalid() {
		Mockito.doThrow(new UserNotFoundException(followerUserId)).when(userService).validateUser(followerUserId);
		followUnfollowService.follow(followerUserId, followeeUserId);
	}

	@Test(expected = UserNotFoundException.class)
	public void testFollowMethodWhereFolloweeIsInvalid() {
		Mockito.doThrow(new UserNotFoundException(followeeUserId)).when(userService).validateUser(followeeUserId);
		followUnfollowService.follow(followerUserId, followeeUserId);
	}

	@Test
	public void testUnFollowSuccess() {
		Mockito.doNothing().when(userService).validateUser(followerUserId);
		Mockito.doNothing().when(userService).validateUser(followeeUserId);

		List<String> followerList = new ArrayList<>();
		followerList.add(followerUserId);

		Mockito.when(followRespository.isAlreadyFollowing(followerUserId, followeeUserId)).thenReturn(followerList);
		Mockito.doNothing().when(followRespository).unfollow(followerUserId, followeeUserId);
		Mockito.doNothing().when(postService).removefolloweesPostsFromFollowersFeed(followerUserId, followeeUserId);

		String[] expectedFollowees = new String[] {};

		Mockito.when(followRespository.getFollowees(followerUserId)).thenReturn(expectedFollowees);

		String[] actualFollowees = followUnfollowService.unfollow(followerUserId, followeeUserId);

		assertArrayEquals(expectedFollowees, actualFollowees);
	}

	@Test(expected = InvalidUnfollowRequestException.class)
	public void testAlreadyNotFollowing() {
		Mockito.doNothing().when(userService).validateUser(followerUserId);
		Mockito.doNothing().when(userService).validateUser(followeeUserId);

		List<String> followerList = new ArrayList<>();

		Mockito.when(followRespository.isAlreadyFollowing(followerUserId, followeeUserId)).thenReturn(followerList);

		followUnfollowService.unfollow(followerUserId, followeeUserId);
	}

	@Test(expected = UserNotFoundException.class)
	public void testUnfollowMethodWhereFollowerIsInvalid() {
		Mockito.doThrow(new UserNotFoundException(followerUserId)).when(userService).validateUser(followerUserId);
		followUnfollowService.unfollow(followerUserId, followeeUserId);
	}

	@Test(expected = UserNotFoundException.class)
	public void testUnfollowMethodWhereFolloweeIsInvalid() {
		Mockito.doThrow(new UserNotFoundException(followeeUserId)).when(userService).validateUser(followeeUserId);
		followUnfollowService.unfollow(followerUserId, followeeUserId);
	}

	@Test
	public void testGetFollowers() {
		List<String> expectedfollowersList = new ArrayList<>();
		expectedfollowersList.add(followerUserId);

		Mockito.when(followRespository.getFollowers(followeeUserId)).thenReturn(expectedfollowersList);

		List<String> actualfollowersList = followUnfollowService.getFollowers(followeeUserId);

		assertEquals(expectedfollowersList, actualfollowersList);
	}
}