package phasebook.post;

import javax.ejb.Remote;

import phasebook.user.PhasebookUser;

@Remote
public interface PostRemote {
	
	public void readUnreadPosts(PhasebookUser entry, Object authId, Object authPass);
	public void removePost(String post, Object authId, Object authPass);
	public Object getUnreadPosts(PhasebookUser entry, Object authId, Object authPass);
	public Post getPostById(Object id, Object authId, Object authPass);
}
