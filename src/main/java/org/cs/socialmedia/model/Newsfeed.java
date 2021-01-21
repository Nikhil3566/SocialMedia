package org.cs.socialmedia.model;

import java.util.ArrayDeque;

public class Newsfeed extends ArrayDeque<Post> {
	private static final long serialVersionUID = 1L;

	public Newsfeed() {
		super();
	}

	@Override
	public void push(Post post) {
		super.push(post);
	}
}
