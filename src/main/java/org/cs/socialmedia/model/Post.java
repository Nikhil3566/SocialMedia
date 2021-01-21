package org.cs.socialmedia.model;

public class Post {
	private Long userId;
	private Long postId;
	private String content;

	public Post(Long userId, Long postId, String content) {
		super();
		this.userId = userId;
		this.postId = postId;
		this.content = content;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getPostId() {
		return postId;
	}

	public String getContent() {
		return content;
	}
}
