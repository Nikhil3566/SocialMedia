package org.cs.socialmedia.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.cs.socialmedia.SocialMediaApplication;
import org.cs.socialmedia.model.FollowUnFollowDTO;
import org.cs.socialmedia.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = SocialMediaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SocialMediaControllerIntegrationTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void testCreatePostForValidUser() {

		// user1 creates a post
		ResponseEntity<String> createPostResponseForAUserThatExistInDataSource = createPost(1L, 10L);

		assertEquals(HttpStatus.CREATED, createPostResponseForAUserThatExistInDataSource.getStatusCode());
	}

	@Test
	public void testCreatePostForInvalidUser() {

		// user100 , who does not exist in social_media database attempts to create a
		// post(ideally this validation should
		// be done at login time)
		ResponseEntity<String> createPostResponseForAUserThatDoesNotExistInDataSource = createPost(100L, 1000L);

		assertEquals(HttpStatus.BAD_REQUEST, createPostResponseForAUserThatDoesNotExistInDataSource.getStatusCode());
	}

	@Test
	public void testFollowValidUsers() {

		// user1 follows user2
		ResponseEntity<Long[]> followResponse12 = follow(1L, 2L);

		assertEquals(HttpStatus.OK, followResponse12.getStatusCode());
		// user1 can see user2 as his followees in response body
		assertThat(followResponse12.getBody()).containsExactly(2L);

		// user1 follows user5
		ResponseEntity<Long[]> followResponse15 = follow(1L, 5L);

		assertEquals(HttpStatus.OK, followResponse15.getStatusCode());
		// user1 can see user2 and user5 as his followees in response body
		assertThat(followResponse15.getBody()).containsExactlyInAnyOrder(2L, 5L);
	}

	@Test
	public void testFollow2UsersAndThenUnFollowOneOfThem() {

		// user2 follows user3
		ResponseEntity<Long[]> followResponse23 = follow(2L, 3L);

		assertEquals(HttpStatus.OK, followResponse23.getStatusCode());
		// user2 can see user3 as his followees in response body
		assertThat(followResponse23.getBody()).containsExactly(3L);

		// user2 follows user5
		ResponseEntity<Long[]> followResponse25 = follow(2L, 5L);

		assertEquals(HttpStatus.OK, followResponse25.getStatusCode());
		// user2 can see user3 and user5 as his followees in response body
		assertThat(followResponse25.getBody()).containsExactlyInAnyOrder(3L, 5L);

		// user2 unfollows user3
		ResponseEntity<Long[]> unfollowResponse23 = unfollow(2L, 3L);

		assertEquals(HttpStatus.OK, unfollowResponse23.getStatusCode());
		// user2 can see user5 as his followee in response body but not user3
		assertThat(unfollowResponse23.getBody()).containsExactly(5L);
	}

	@Test
	public void testFollowAnAlreadyFollowedUser() {
		/*
		 * Assumption : 1.Technically : Following an already followed user is
		 * idempotent(PUT request). 2.Real Time : Already followed users will come in
		 * followee section and follow button will be disabled for them.
		 */

		follow(4L, 5L);
		ResponseEntity<Long[]> followResponse2 = follow(4L, 5L);

		assertEquals(HttpStatus.OK, followResponse2.getStatusCode());
		assertThat(followResponse2.getBody()).containsExactly(5L);
	}
	
	@Test
	public void testUNFollowAnAlreadyUnFollowedUser() {
		
		FollowUnFollowDTO unfollowRequestBody = new FollowUnFollowDTO(5L, 3L);

		HttpEntity<FollowUnFollowDTO> unfollowEntity = new HttpEntity<FollowUnFollowDTO>(unfollowRequestBody, headers);

		ResponseEntity unfollowResponse = restTemplate.exchange(createURLWithPort("/socialmedia/unfollow/"),
				HttpMethod.PUT, unfollowEntity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, unfollowResponse.getStatusCode());
	}

	@Test
	public void testValidUserFollowsInvalidUser() {
		// user1 follows user100(who does not exist in database)

		FollowUnFollowDTO followRequestBody = new FollowUnFollowDTO(1L, 100L);

		HttpEntity<FollowUnFollowDTO> followEntity = new HttpEntity<FollowUnFollowDTO>(followRequestBody, headers);

		ResponseEntity followResponse1_100 = restTemplate.exchange(createURLWithPort("/socialmedia/follow/"),
				HttpMethod.PUT, followEntity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, followResponse1_100.getStatusCode());
	}

	@Test
	public void testInValidUserFollowsValidUser() {
		// user100(who does not exist in database) follows user1(who does not exist in
		// database)
		FollowUnFollowDTO followRequestBody = new FollowUnFollowDTO(100L, 1L);

		HttpEntity<FollowUnFollowDTO> followEntity = new HttpEntity<FollowUnFollowDTO>(followRequestBody, headers);

		ResponseEntity followResponse100_1 = restTemplate.exchange(createURLWithPort("/socialmedia/follow/"),
				HttpMethod.PUT, followEntity, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, followResponse100_1.getStatusCode());
	}

	@Test
	public void test20RecentNewsFeeds() {

		// Step 1. user1 follows user2
		follow(1L, 2L);

		// Step 2. user2 creates a post
		createPost(2L, 21L);

		// Step 3. user1 follows user3
		follow(1L, 3L);

		// Step 4. user3 creates 5 posts
		createPost(3L, 31L);
		createPost(3L, 32L);
		createPost(3L, 33L);
		createPost(3L, 34L);
		createPost(3L, 35L);

		// Step 5. user1 creates 5 posts
		createPost(1L, 11L);
		createPost(1L, 12L);
		createPost(1L, 13L);
		createPost(1L, 14L);
		createPost(1L, 15L);

		// Step 6. user1 follows user4
		follow(1L, 4L);

		// Step 7. user4 creates 5 posts
		createPost(4L, 41L);
		createPost(4L, 42L);
		createPost(4L, 43L);
		createPost(4L, 44L);
		createPost(4L, 45L);

		// Step 8. user1 follows user5
		follow(1L, 5L);

		// Step 9. user5 creates 10 posts
		createPost(5L, 50L);
		createPost(5L, 51L);
		createPost(5L, 52L);
		createPost(5L, 53L);
		createPost(5L, 54L);
		createPost(5L, 55L);
		createPost(5L, 56L);
		createPost(5L, 57L);
		createPost(5L, 58L);
		createPost(5L, 59L);

		// Step 10. user1 checks his newsfeed
		assertArrayEquals(new Long[] { 59L, 58L, 57L, 56L, 55L, 54L, 53L, 52L, 51L, 50L, 45L, 44L, 43L, 42L, 41L, 15L,
				14L, 13L, 12L, 11L }, checkNewsFeed(1L).getBody());
	}

	@Test
	public void testAValidUserFollowsAndUnfollows4ValidUsersAndKeepsCheckingHisNewsFeed() {

		// Step 1. user1 follows user2
		follow(1L, 2L);

		// Step 2. user2 creates a post
		createPost(2L, 21L);

		// Step 3. user2 checks his news feed
		assertArrayEquals(new Long[] { 21L }, checkNewsFeed(2L).getBody());

		// Step 4. user1 checks his news feed
		assertArrayEquals(new Long[] { 21L }, checkNewsFeed(1L).getBody());

		// Step 5. user1 follows user3
		follow(1L, 3L);

		// Step 6. user3 creates 5 posts
		createPost(3L, 31L);
		createPost(3L, 32L);
		createPost(3L, 33L);
		createPost(3L, 34L);
		createPost(3L, 35L);

		// Step 7. user1 checks his newsfeed
		assertArrayEquals(new Long[] { 35L, 34L, 33L, 32L, 31L, 21L }, checkNewsFeed(1L).getBody());

		// Step 8. user1 creates 5 posts
		createPost(1L, 11L);
		createPost(1L, 12L);
		createPost(1L, 13L);
		createPost(1L, 14L);
		createPost(1L, 15L);

		// Step 9. user1 checks his newsfeed
		assertArrayEquals(new Long[] { 15L, 14L, 13L, 12L, 11L, 35L, 34L, 33L, 32L, 31L, 21L },
				checkNewsFeed(1L).getBody());

		// Step 10. user1 follows user4
		follow(1L, 4L);

		// Step 11. user4 creates 5 posts
		createPost(4L, 41L);
		createPost(4L, 42L);
		createPost(4L, 43L);
		createPost(4L, 44L);
		createPost(4L, 45L);

		// Step 12. user1 checks his newsfeed
		assertArrayEquals(new Long[] { 45L, 44L, 43L, 42L, 41L, 15L, 14L, 13L, 12L, 11L, 35L, 34L, 33L, 32L, 31L, 21L },
				checkNewsFeed(1L).getBody());

		// Step 13. user1 follows user5
		follow(1L, 5L);

		// Step 14. user5 creates 10 posts
		createPost(5L, 50L);
		createPost(5L, 51L);
		createPost(5L, 52L);
		createPost(5L, 53L);
		createPost(5L, 54L);
		createPost(5L, 55L);
		createPost(5L, 56L);
		createPost(5L, 57L);
		createPost(5L, 58L);
		createPost(5L, 59L);

		// Step 15. user1 checks his newsfeed
		assertArrayEquals(new Long[] { 59L, 58L, 57L, 56L, 55L, 54L, 53L, 52L, 51L, 50L, 45L, 44L, 43L, 42L, 41L, 15L,
				14L, 13L, 12L, 11L }, checkNewsFeed(1L).getBody());

		// Step 16. user1 unfollows user2 and user4
		unfollow(1L, 2L);
		unfollow(1L, 4L);

		// Step 17. user1 checks his newsfeed
		assertArrayEquals(new Long[] { 59L, 58L, 57L, 56L, 55L, 54L, 53L, 52L, 51L, 50L, 15L, 14L, 13L, 12L, 11L, 35L,
				34L, 33L, 32L, 31L }, checkNewsFeed(1L).getBody());

	}

	@Test
	public void testAnInvalidUserChecksHisNewsFeed() {
		HttpEntity<Long> entity = new HttpEntity<Long>(100L, headers);

		ResponseEntity response = restTemplate.exchange(
				createURLWithPort(String.format("/socialmedia/newsfeed/%s", 100L)), HttpMethod.GET, entity,
				String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	private ResponseEntity<String> createPost(Long userId, Long postId) {

		Post post = new Post(userId, postId, String.format("This is user: %d's post number %d: ", userId, postId));

		HttpEntity<Post> entity = new HttpEntity<Post>(post, headers);

		return restTemplate.exchange(createURLWithPort("/socialmedia/posts/"), HttpMethod.POST, entity, String.class);
	}

	private ResponseEntity<Long[]> checkNewsFeed(Long userId) {

		HttpEntity<Long> entity = new HttpEntity<Long>(userId, headers);

		return restTemplate.exchange(createURLWithPort(String.format("/socialmedia/newsfeed/%s", userId)),
				HttpMethod.GET, entity, Long[].class);
	}

	private ResponseEntity<Long[]> follow(Long followerId, Long followeeId) {
		FollowUnFollowDTO followRequestBody = new FollowUnFollowDTO(followerId, followeeId);

		HttpEntity<FollowUnFollowDTO> followEntity = new HttpEntity<FollowUnFollowDTO>(followRequestBody, headers);

		return restTemplate.exchange(createURLWithPort("/socialmedia/follow/"), HttpMethod.PUT, followEntity,
				Long[].class);
	}

	private ResponseEntity<Long[]> unfollow(Long followerId, Long followeeId) {
		FollowUnFollowDTO unfollowRequestBody = new FollowUnFollowDTO(followerId, followeeId);

		HttpEntity<FollowUnFollowDTO> unfollowEntity = new HttpEntity<FollowUnFollowDTO>(unfollowRequestBody, headers);

		return restTemplate.exchange(createURLWithPort("/socialmedia/unfollow/"), HttpMethod.PUT, unfollowEntity,
				Long[].class);
	}

}
