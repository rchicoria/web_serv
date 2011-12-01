package phasebook.friendship;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface FriendshipRemote {

	
	public int friendshipStatus(int user_a_id, int user_b_id, Object authId, Object authPass);
	public Friendship searchFriendship(int user_a_id, int user_b_id, Object authId, Object authPass);
	public void acceptFriendship(int toUserId, int fromUserId, Object authId, Object authPass);
	public List<?> getNewFriendshipAcceptances(int userId, Object authId, Object authPass);
	public List<?> getNewFriendshipInvites(int userId, Object authId, Object authPass);
	public void readUnreadFriendshipAcceptances(int userId, Object authId, Object authPass);
	public void readUnreadFriendshipInvites(int userId, Object authId, Object authPass);
	public void invite(int hostUserId, int invitedUserId, Object authId, Object authPass);
	public List<?> getUserFriendships(int userId, Object authId, Object authPass);
	
}
