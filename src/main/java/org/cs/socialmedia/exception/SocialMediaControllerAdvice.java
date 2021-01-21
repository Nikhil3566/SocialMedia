package org.cs.socialmedia.exception;

import org.cs.socialmedia.enums.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SocialMediaControllerAdvice {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
		ApiErrorResponse response = new ApiErrorResponse(ErrorCodes.INVALID_USER.getResponseCode(),
				String.format(ErrorCodes.INVALID_USER.getResponseMessage(), ex.getId()));
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidUnfollowRequestException.class)
	public ResponseEntity<ApiErrorResponse> handleInvalidUnfollowRequestException(InvalidUnfollowRequestException ex) {
		ApiErrorResponse response = new ApiErrorResponse(ErrorCodes.INVALID_UNFOLLOW_REQUEST.getResponseCode(),
				String.format(ErrorCodes.INVALID_UNFOLLOW_REQUEST.getResponseMessage(), ex.getFollowerId(),
						ex.getFolloweeId()));
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}
}
