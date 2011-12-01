package phasebook.post;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface PostRemote {
	
	public void readUnreadPosts(int entry_id, Object authId, Object authPass);
	public void removePost(String post, Object authId, Object authPass);
	public List<?> getUnreadPosts(int entry_id, Object authId, Object authPass);
	public Post getPostById(Object id, Object authId, Object authPass);
}
