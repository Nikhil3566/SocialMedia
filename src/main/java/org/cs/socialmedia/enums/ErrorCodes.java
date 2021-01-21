package org.cs.socialmedia.enums;

public enum ErrorCodes {

	INVALID_USER(400, "No user found with ID: %d"),
	INVALID_UNFOLLOW_REQUEST(401, "User: %d is already not following User: %d");

	private int responseCode;
	private String responseMessage;

	private ErrorCodes(int responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}
}
