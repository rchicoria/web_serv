package containers;

import info.PostDetailsInfo;

import java.util.List;

public class PostsContainer {
	List<PostDetailsInfo> posts;
	
	public PostsContainer() {
		super();
	}

	public PostsContainer(List<PostDetailsInfo> posts) {
		super();
		this.posts = posts;
	}

	public List<PostDetailsInfo> getPosts() {
		return posts;
	}

	public void setPosts(List<PostDetailsInfo> posts) {
		this.posts = posts;
	}
}
