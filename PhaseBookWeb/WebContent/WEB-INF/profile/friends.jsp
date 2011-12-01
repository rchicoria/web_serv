<%@ page import="phasebook.controller.*"%>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.photo.*" %>
<%@ page import="phasebook.friendship.*" %>
<%@ page import="java.util.List" %>

<%
	String id;
	try {
		id = "" + Utils.getUserBean().getUserById(request.getParameter("id"),
				session.getAttribute("id"), session.getAttribute("password")).getId();
	} catch (Exception e) {
		id = session.getAttribute("id").toString();
	}
	PhasebookUserRemote userBean = Utils.getUserBean();
	FriendshipRemote friendshipBean = Utils.getFriendshipBean();
	List<?> friends = friendshipBean.getUserFriendships(Integer.parseInt(id),
			session.getAttribute("id"), session.getAttribute("password"));
	if (friends.size() == 0) {
%>
		<p>This user has no friends.</p>
<%
	}
	else for (int i=0; i<friends.size(); i++) {
		PhasebookUser friend;
		Friendship friendship = (Friendship) friends.get(i);
		if (friendship.getHostUserId() != Integer.parseInt(id))
			friend = userBean.getUserById(friendship.getHostUserId(), session.getAttribute("id"), session.getAttribute("password"));
		else
			friend = userBean.getUserById(friendship.getInvitedUserId(), session.getAttribute("id"), session.getAttribute("password"));
		
%>
		<table style="width: 100%">
			<tr>
				<td width="60">
					<% if (friend.getPhotoId()!=-1){
						PhotoRemote photoBean = Utils.getPhotoBean();
						String photoURL = Utils.MAIN_PATH + friend.getId() + "/"+photoBean.getPhotoById(""+friend.getPhotoId(), session.getAttribute("id"), session.getAttribute("password")).getName();
					%>
						<%= Utils.a("user&id="+friend.getId(), Utils.smallImg(photoURL)) %>
					<% } %>
				</td>
				<td>
					<b class="user"><%= Utils.a("user&id="+friend.getId(), Utils.text(friend.getName())) %></b><br />
					<i><%= Utils.text(friend.getEmail()) %></i>
				</td>
			</tr>
		</table>
<% } %>