<%@ page import="phasebook.controller.*"%>
<%@ page import="phasebook.post.*" %>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.photo.*" %>
<%@ page import="java.util.*" %>

<%
	PhasebookUserRemote userBean = Utils.getUserBean();
	PhasebookUser user;
	Object userId;
	PhasebookUser me=userBean.getUserById(session.getAttribute("id"),
			session.getAttribute("id"), session.getAttribute("password"));
	
	if(request.getParameter("id") == null){
		userId =  session.getAttribute("id");
		user = userBean.getUserById(userId,
				session.getAttribute("id"), session.getAttribute("password"));
	}
	else{
		userId =  request.getParameter("id");
		try {
			Utils.getUserBean().getUserById(request.getParameter("id"),
					session.getAttribute("id"), session.getAttribute("password")).getName();
			user = userBean.getUserById(userId,
					session.getAttribute("id"), session.getAttribute("password"));
		} catch (Exception e) {
			userId =  session.getAttribute("id");
			user = userBean.getUserById(session.getAttribute("id"),
					session.getAttribute("id"), session.getAttribute("password"));
		}
	}

	List<?> posts = null;
	if (Utils.getFriendshipBean().friendshipStatus(me.getId(), user.getId(),
			session.getAttribute("id"), session.getAttribute("password")) == 3 || me.equals(user) )
		posts = userBean.getUserReceivedPosts(userId,
				session.getAttribute("id"), session.getAttribute("password"));
	else
		posts = userBean.getUserPublicPosts(userId,
				session.getAttribute("id"), session.getAttribute("password"));
	
	if (posts.size() == 0) {
%>
		<p>This user has no posts.</p>
<%
	}
	for (int i=posts.size()-1; i>=0; i--) {
		Post post = (Post) posts.get(i);
		PhasebookUser sender = userBean.getUserById(post.getFromUserId(), session.getAttribute("id"), session.getAttribute("password"));
%>
	<table style="width: 100%">
		<tr>
			<td width="60" style="vertical-align: top">
				<% if (sender.getPhotoId()!=-1){
					PhotoRemote photoBean = Utils.getPhotoBean();
					String photoURL = Utils.MAIN_PATH + sender.getId() + "/"+photoBean.getPhotoById(""+sender.getPhotoId(), session.getAttribute("id"), session.getAttribute("password")).getName();
				%>
					<%= Utils.a("user&id="+sender.getId(), Utils.smallImg(photoURL)) %>
				<% } %>
			</td>
			<td>
				<% if (me.equals(user)) { %>
				<form method="POST" action="RemovePostForm">
				<input type="hidden" name="postId" value="<%= post.getId() %>"/>
				<input type="hidden" name="userId" value="<%= userId %>"/>
				<input type="submit" value="x" name="B0" style="float: right; font-size: 80%; background: white; color: #444; border: 1px solid #444; padding: 3px; font-weight: normal">
				</form>
				<% } %>
	
				<b class="user"><%= Utils.a("user&id="+sender.getId(), Utils.text(sender.getName())) %></b>
				<% if (post.isPrivate_()) { %><i>(private)</i><% } %><br />
				<% if (post.getPhotoId()!=-1){
					PhotoRemote photoBean = Utils.getPhotoBean();
					String photoURL = Utils.MAIN_PATH+userId.toString()+"/"+photoBean.getPhotoById(""+post.getPhotoId(), session.getAttribute("id"), session.getAttribute("password")).getName();
				%>
					<br /> <%= Utils.aAbsolute(photoURL, Utils.img(photoURL)) %>
				<%} %>
				<br /><%= Utils.text(post.getText()) %>
			</td>
		</tr>
	</table>
<%
	}
%>