package org.cs.socialmedia.controller;

import java.util.List;

import org.cs.socialmedia.model.FollowUnFollowDTO;
import org.cs.socialmedia.model.Post;
import org.cs.socialmedia.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialMediaController {

	@Autowired
	SocialMediaService service;

	@PostMapping("/posts")
	public ResponseEntity<Post> createPost(@RequestBody Post post) {
		service.createPost(post);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/newsfeed/{userId}")
	public ResponseEntity<List<Long>> getNewsFeed(@PathVariable("userId") Long userId) {
		return new ResponseEntity<>(service.getNewsFeed(userId), HttpStatus.OK);
	}

	@PutMapping("/follow")
	public ResponseEntity<List<Long>> follow(@RequestBody FollowUnFollowDTO followDTO) {
		return new ResponseEntity<>(service.follow(followDTO.getFollowerId(), followDTO.getFolloweeId()),
				HttpStatus.OK);
	}

	@PutMapping("/unfollow")
	public ResponseEntity<List<Long>> unfollow(@RequestBody FollowUnFollowDTO unFollowDTO) {
		return new ResponseEntity<>(service.unfollow(unFollowDTO.getFollowerId(), unFollowDTO.getFolloweeId()),
				HttpStatus.OK);
	}
}
