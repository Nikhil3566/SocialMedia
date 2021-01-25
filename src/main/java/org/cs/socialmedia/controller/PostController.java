package org.cs.socialmedia.controller;

import org.cs.socialmedia.model.dto.PostDTO;
import org.cs.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

	@Autowired
	PostService postService;

	@PostMapping("/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post) {
		postService.createPost(post);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/newsfeed/{userId}")
	public ResponseEntity<String[]> getNewsFeed(@PathVariable("userId") String userId) {
		return new ResponseEntity<>(postService.getNewsFeed(userId), HttpStatus.OK);
	}
}
