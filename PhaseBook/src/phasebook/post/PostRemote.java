package phasebook.post;

import java.util.List;

import javax.ejb.Remote;

import phasebook.user.PhasebookUser;

@Remote
public interface PostRemote {
	
	public void readUnreadPosts(int entry_id, Object authId, Object authPass);
	public void removePost(String post, Object authId, Object authPass);
	public Object getUnreadPosts(int entry_id, Object authId, Object authPass);
	public Post getPostById(Object id, Object authId, Object authPass);
	public List<Post> getUserReceivedPosts(Object userId, Object authId, Object authPass);
	public List getUserPublicPosts(Object userId, Object authId, Object authPass);
	public void addPost(PhasebookUser from, PhasebookUser to, String text, String privacy, Object authId, Object authPass);
	public void addPost(PhasebookUser from, PhasebookUser to, String text, String photoLink, String privacy, Object authId, Object authPass);
	public int getNUnreadUserPosts(PhasebookUser user, Object authId, Object authPass);
}
