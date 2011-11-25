package phasebook.user;
import java.util.List;

import javax.ejb.Remote;

import phasebook.photo.Photo;
import phasebook.post.Post;

@Remote
public interface PhasebookUserRemote {
	
	public int create(String name, String email, String password);
	public int login(String email, String password);
	public PhasebookUser getUserById(Object id, Object authId, Object authPass);
	public List getUsersFromSearch(Object search, Object authId, Object authPass);
	public List<Post> getUserReceivedPosts(Object userId, Object authId, Object authPass);
	public void addPost(PhasebookUser from, PhasebookUser to, String text, String privacy, Object authId, Object authPass);
	public void invite(PhasebookUser hostUser, PhasebookUser invitedUser, Object authId, Object authPass);
	public void addPost(PhasebookUser from, PhasebookUser to, String text, String photoLink, String privacy, Object authId, Object authPass);
	public List getUserPublicPosts(Object userId, Object authId, Object authPass);
	public Photo addPhoto(String photoLink, Object authId, Object authPass);
	public void setProfilePicture(PhasebookUser user, Photo photo, Object authId, Object authPass);
	public void deposit(Object id, Float money, Object authId, Object authPass);
	public List<PhasebookUser> getUserFriendships(String id, Object authId, Object authPass);
	public void editAccount(Object id, String name, String photo, String password, Object authId, Object authPass);
	public int getNUnreadUserPosts(PhasebookUser user, Object authId, Object authPass);
	public Photo getUserPhoto(PhasebookUser user, Object authId, Object authPass);
}
