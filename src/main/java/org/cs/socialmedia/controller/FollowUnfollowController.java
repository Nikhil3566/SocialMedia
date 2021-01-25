package org.cs.socialmedia.controller;

import org.cs.socialmedia.model.dto.FollowUnFollowDTO;
import org.cs.socialmedia.service.FollowUnfollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowUnfollowController {

	@Autowired
	FollowUnfollowService followUnfollowService;

	@PutMapping("/follow")
	public ResponseEntity<String[]> follow(@RequestBody FollowUnFollowDTO followDTO) {
		return new ResponseEntity<>(followUnfollowService.follow(followDTO.getFollowerId(), followDTO.getFolloweeId()),
				HttpStatus.OK);
	}

	@PutMapping("/unfollow")
	public ResponseEntity<String[]> unfollow(@RequestBody FollowUnFollowDTO unFollowDTO) {
		return new ResponseEntity<>(followUnfollowService.unfollow(unFollowDTO.getFollowerId(), unFollowDTO.getFolloweeId()),
				HttpStatus.OK);
	}
}
