package org.cs.socialmedia.model.dto;

public class PostDTO {
	private String userId;
	private String postId;
	private String content;

	public PostDTO(String userId, String postId, String content) {
		super();
		this.userId = userId;
		this.postId = postId;
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public String getPostId() {
		return postId;
	}

	public String getContent() {
		return content;
	}
}
