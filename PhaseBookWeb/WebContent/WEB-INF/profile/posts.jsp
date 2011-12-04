<%@ page import="phasebook.controller.*"%>
<%@ page import="phasebook.post.*" %>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.photo.*" %>
<%@ page import="client.artefact.*" %>
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
		userId = Integer.parseInt((String)request.getParameter("id"));
		try {
			Utils.getUserBean().getUserById(request.getParameter("id"),
					session.getAttribute("id"), session.getAttribute("password")).getName();
			user = userBean.getUserById(userId,
					session.getAttribute("id"), session.getAttribute("password"));
		} catch (Exception e) {
			userId = session.getAttribute("id");
			user = userBean.getUserById(session.getAttribute("id"),
					session.getAttribute("id"), session.getAttribute("password"));
		}
	}
	
	MethodsService cs = new MethodsService();
	Methods m = cs.getMethodsPort();
	List<?> posts = null;
	int friend = 0;
	if (Utils.getFriendshipBean().friendshipStatus(me.getId(), user.getId(),
			session.getAttribute("id"), session.getAttribute("password")) == 3 || me.equals(user) )
		friend = 1;
	posts = m.getPosts(((Integer)session.getAttribute("id")).intValue(),
			(String)session.getAttribute("token"), ((Long)session.getAttribute("expiration")).longValue(), 
			(new Date()).getTime(), ((Integer)userId).intValue(), friend).getPosts();
	// falhou autenticação
	if(posts.size()!=0 && ((PostDetailsInfo)posts.get(0)).getPostId()==0){
		Utils.auth(session, response, request);
		posts = m.getPosts(((Integer)session.getAttribute("id")).intValue(),
				(String)session.getAttribute("token"), ((Long)session.getAttribute("expiration")).longValue(), 
				(new Date()).getTime(), ((Integer)userId).intValue(), friend).getPosts();
	}
		
	if (posts.size() == 0) {
%>
		<p>This user has no posts.</p>
<%
	}
	for (int i=posts.size()-1; i>=0; i--) {
		PostDetailsInfo post = (PostDetailsInfo) posts.get(i);
%>
	<table style="width: 100%">
		<tr>
			<td width="60" style="vertical-align: top">
				<% if (post.getUserPhotoId()!=-1){
					String photoURL = Utils.MAIN_PATH + post.getUserId() + "/"+post.getUserPhotoName();
				%>
					<%=Utils.a("user&id="+post.getUserId(), Utils.smallImg(photoURL)) %>
				<% } %>
			</td>
			<td>
				<% if (me.equals(user)) { %>
				<form method="POST" action="RemovePostForm">
				<input type="hidden" name="postId" value="<%= post.getPostId() %>"/>
				<input type="hidden" name="userId" value="<%= userId %>"/>
				<input type="submit" value="x" name="B0" style="float: right; font-size: 80%; background: white; color: #444; border: 1px solid #444; padding: 3px; font-weight: normal">
				</form>
				<% } %>
	
				<b class="user"><%= Utils.a("user&id="+post.getUserId(), Utils.text(post.getUserName())) %></b>
				<% if (post.isPostPrivate()) { %><i>(private)</i><% } %><br />
				<% if (post.getPostPhotoId()!=-1){
					String photoURL = Utils.MAIN_PATH+userId.toString()+"/"+post.getPostPhotoName();
				%>
					<br /> <%= Utils.aAbsolute(photoURL, Utils.img(photoURL)) %>
				<%} %>
				<br /><%= Utils.text(post.getPostText()) %>
			</td>
		</tr>
	</table>
<%
	}
%>