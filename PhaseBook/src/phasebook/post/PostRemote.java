package phasebook.post;

import info.UserInfo;

import java.util.List;

import javax.ejb.Remote;

import phasebook.user.PhasebookUser;

@Remote
public interface PostRemote {
	
	public void readUnreadPosts(int entry_id, Object authId, Object authPass);
	public void removePost(String post, Object authId, Object authPass);
	public List<?> getUnreadPosts(int entry_id, Object authId, Object authPass);
	public Post getPostById(Object id, Object authId, Object authPass);
	public List<Post> getUserReceivedPosts(Object userId, Object authId, Object authPass);
	public List getUserPublicPosts(Object userId, Object authId, Object authPass);
	public void addPost(UserInfo from, UserInfo to, String text, String privacy);
	public void addPost(UserInfo from, UserInfo to, String text, String photoLink, int photoId, String privacy);
	public int getNUnreadUserPosts(UserInfo user, Object authId, Object authPass);
}
