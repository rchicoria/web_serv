package phasebook.friendship;

import javax.ejb.Remote;

import phasebook.user.PhasebookUser;

@Remote
public interface FriendshipRemote {

	
	public int friendshipStatus(PhasebookUser user_a, PhasebookUser user_b, Object authId, Object authPass);
	public Friendship searchFriendship(PhasebookUser user_a, PhasebookUser user_b, Object authId, Object authPass);
	public void acceptFriendship(PhasebookUser toUser, PhasebookUser fromUser, Object authId, Object authPass);
	public Object getNewFriendshipAcceptances(PhasebookUser entry, Object authId, Object authPass);
	public Object getNewFriendshipInvites(PhasebookUser entry, Object authId, Object authPass);
	public void readUnreadFriendshipAcceptances(PhasebookUser entry, Object authId, Object authPass);
	public void readUnreadFriendshipInvites(PhasebookUser entry, Object authId, Object authPass);
	
}
