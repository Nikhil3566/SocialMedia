package org.cs.socialmedia.enums;

public enum ErrorCodes {

	INVALID_USER(400, "No user found with ID: %s"),
	INVALID_FOLLOW_REQUEST(401, "User: %s is already following User: %s"),
	INVALID_UNFOLLOW_REQUEST(402, "User: %s is already not following User: %s");

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
