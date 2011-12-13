<%@ page import="phasebook.controller.*"%>
<%@ page import="javax.naming.*" %>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.post.*" %>
<%@ page import="phasebook.friendship.*" %>
<%@ page import="java.util.*" %>

<% 
	PhasebookUserRemote userBean = Utils.getUserBean();
	PhasebookUser user;
	Object userId;
	Friendship fs;
	PhasebookUser me = userBean.getUserById(session.getAttribute("id"),
			session.getAttribute("id"), session.getAttribute("password"));
	
	int relationshipType = -1;
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
			relationshipType = Utils.getFriendshipBean().friendshipStatus(me,user,
					session.getAttribute("id"), session.getAttribute("password"));
		} catch (Exception e) {
			userId =  session.getAttribute("id");
			user = userBean.getUserById(session.getAttribute("id"),
					session.getAttribute("id"), session.getAttribute("password"));
		}
	}
	
	
%>

<form method="POST" action="CreateFriendshipForm">
	<input type="hidden" name="toUser" value="<%= userId.toString() %>"/>
	<input type="hidden" name="relationship" value="<%= relationshipType %>"/>
<%
	if(request.getParameter("id")!=null && !request.getParameter("id").toString().equals(session.getAttribute("id"))){
   		if(relationshipType==0){%>
			<input type="submit" value="Add Friend" name="F0">
<%		}else if(relationshipType==1){%>
			<input type="submit" value="Friendship request sent" name="F1" disabled="disabled">
<%		}else if(relationshipType==2){ %>
			<input type="submit" value="Accept Friendship" name="F2">	
<%		}else if(relationshipType==3 && relationshipType==-1){}}%>

</form>

<table width="100%">
	<tr>
		<td width="120">
			<% if (userBean.getUserPhoto(user,
					session.getAttribute("id"), session.getAttribute("password"))!=null){ 
				String photoURL = Utils.MAIN_PATH + userId.toString() + "/"+userBean.getUserPhoto(user,
						session.getAttribute("id"), session.getAttribute("password")).getName();
			%>
				<%= Utils.a("user&id="+userId.toString(), Utils.img(photoURL)) %>
			<% } %>
		</td>
		<td>
			<h1><%= Utils.text(user.getName()) %></h1> 
			<p class="tip"><%= Utils.text(user.getEmail()) %></p>
		</td>
	</tr>
</table>

<%
	if (session.getAttribute("error") != null)
	{
%>
		<p style="color:red"><%= session.getAttribute("error") %></p>
<%
		session.removeAttribute("error");
	}
%>

<%
	String post = "";
	String privacy = "0";
	try {
		post = session.getAttribute("post").toString();
		session.removeAttribute("post");
		privacy = session.getAttribute("privacy").toString();
		session.removeAttribute("privacy");
	} catch (Exception e) {}
%>

<form enctype="multipart/form-data" method="POST" action="CreatePostForm" style="padding: 0 50px 0 50px;">
	<textarea id="post" name="post" placeholder="What's on your mind?"></textarea>
	<input type="hidden" name="toUser" value="<%= userId.toString() %>"/>
	<table width="100%">
		<tr>
			<td>
				Upload image: <input type="file" name="file1">
			</td>
			<td style="text-align: right">
				<select name="privacy">
					<option value="0" <% if (privacy.compareTo("0")==0) { %>selected<% } %>>Public</option>
					<% if (Utils.getFriendshipBean().friendshipStatus(me, user,
							session.getAttribute("id"), session.getAttribute("password")) == 3 || me.equals(user) ){ %>
						<option value="1" <% if (privacy.compareTo("1")==0) { %>selected<% } %>>Private</option>
					<% } %>
				</select>
				<input type="submit" value="Post" name="B1">
			</td>
		</tr>
	</table>
</form>

<ul id="profiletabs">
	<li id="tab1" onclick="selectPosts()">Posts</li>
	<li id="tab2" onclick="selectPhotos()">Gallery</li>
	<li id="tab3" onclick="selectFriends()">Friends</li>
</ul>

<div id="tabposts" style="display: none"><% pageContext.include("/WEB-INF/profile/posts.jsp"); %></div>
<div id="tabphotos" style="display: none"><% pageContext.include("/WEB-INF/profile/photos.jsp"); %></div>
<div id="tabfriends" style="display: none"><% pageContext.include("/WEB-INF/profile/friends.jsp"); %></div>
<script>
	selectPosts();
</script>
