package org.cs.socialmedia.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.cs.socialmedia.controller.FollowUnfollowController;
import org.cs.socialmedia.exception.InvalidFollowRequestException;
import org.cs.socialmedia.exception.InvalidUnfollowRequestException;
import org.cs.socialmedia.exception.UserNotFoundException;
import org.cs.socialmedia.model.dto.FollowUnFollowDTO;
import org.cs.socialmedia.service.FollowUnfollowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class FollowUnfollowControllerTest {

	@InjectMocks
	FollowUnfollowController followUnfollowController;

	@Mock
	FollowUnfollowService followUnfollowService;

	private String followerUserId = "followerUserId";
	private String followeeUserId = "followeeUserId";

	@Test
	public void testFollowSuccess() {
		String[] followeeList = new String[] { followeeUserId };
		Mockito.when(followUnfollowService.follow(followerUserId, followeeUserId)).thenReturn(followeeList);
		ResponseEntity<String[]> response = followUnfollowController
				.follow(new FollowUnFollowDTO(followerUserId, followeeUserId));
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test(expected = InvalidFollowRequestException.class)
	public void testAlreadyFollowing() {
		Mockito.doThrow(new InvalidFollowRequestException(followerUserId, followeeUserId)).when(followUnfollowService)
				.follow(followerUserId, followeeUserId);
		followUnfollowController.follow(new FollowUnFollowDTO(followerUserId, followeeUserId));
	}

	@Test(expected = UserNotFoundException.class)
	public void testFollowMethodWhereFollowerIsInvalid() {
		Mockito.doThrow(new UserNotFoundException(followerUserId)).when(followUnfollowService).follow(followerUserId,
				followeeUserId);
		followUnfollowController.follow(new FollowUnFollowDTO(followerUserId, followeeUserId));
	}

	@Test(expected = UserNotFoundException.class)
	public void testFollowMethodWhereFolloweeIsInvalid() {
		Mockito.doThrow(new UserNotFoundException(followeeUserId)).when(followUnfollowService).follow(followerUserId,
				followeeUserId);
		followUnfollowController.follow(new FollowUnFollowDTO(followerUserId, followeeUserId));
	}

	@Test
	public void testUnFollowSuccess() {
		String[] followeeList = new String[] {};
		Mockito.when(followUnfollowService.unfollow(followerUserId, followeeUserId)).thenReturn(followeeList);
		ResponseEntity<String[]> response = followUnfollowController
				.unfollow(new FollowUnFollowDTO(followerUserId, followeeUserId));
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test(expected = InvalidUnfollowRequestException.class)
	public void testAlreadyNotFollowing() {
		Mockito.doThrow(new InvalidUnfollowRequestException(followerUserId, followeeUserId)).when(followUnfollowService)
				.unfollow(followerUserId, followeeUserId);
		followUnfollowController.unfollow(new FollowUnFollowDTO(followerUserId, followeeUserId));
	}

	@Test(expected = UserNotFoundException.class)
	public void testUnFollowMethodWhereFollowerIsInvalid() {
		Mockito.doThrow(new UserNotFoundException(followerUserId)).when(followUnfollowService).unfollow(followerUserId,
				followeeUserId);
		followUnfollowController.unfollow(new FollowUnFollowDTO(followerUserId, followeeUserId));
	}

	@Test(expected = UserNotFoundException.class)
	public void testUnFollowMethodWhereFolloweeIsInvalid() {
		Mockito.doThrow(new UserNotFoundException(followeeUserId)).when(followUnfollowService).unfollow(followerUserId,
				followeeUserId);
		followUnfollowController.unfollow(new FollowUnFollowDTO(followerUserId, followeeUserId));
	}

}
