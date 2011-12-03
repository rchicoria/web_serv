<%@ page import="phasebook.controller.*"%>
<%@ page import="javax.naming.*" %>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.post.*" %>
<%@ page import="phasebook.photo.*" %>
<%@ page import="phasebook.friendship.*" %>
<%@ page import="client.artefact.*" %>
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
		userId = Integer.parseInt((String)request.getParameter("id"));
		try {
			Utils.getUserBean().getUserById(request.getParameter("id"),
					session.getAttribute("id"), session.getAttribute("password")).getName();
			user = userBean.getUserById(userId,
					session.getAttribute("id"), session.getAttribute("password"));
			relationshipType = Utils.getFriendshipBean().friendshipStatus(me.getId(),user.getId(),
					session.getAttribute("id"), session.getAttribute("password"));
		} catch (Exception e) {
			userId =  session.getAttribute("id");
			user = userBean.getUserById(session.getAttribute("id"),
					session.getAttribute("id"), session.getAttribute("password"));
		}
	}
	
	String posttext = "";
	String privacy = "0";
	try {
		posttext = session.getAttribute("post").toString();
		session.removeAttribute("post");
		privacy = session.getAttribute("privacy").toString();
		session.removeAttribute("privacy");
	} catch (Exception e) {}
	
	MethodsService cs = new MethodsService();
	Methods m = cs.getMethodsPort();
	List<?> posts = null;
	int friend = 0;
	if (Utils.getFriendshipBean().friendshipStatus(me.getId(), user.getId(),
			session.getAttribute("id"), session.getAttribute("password")) == 3 || me.equals(user) )
		friend = 1;
	posts = m.getPosts(((Integer)session.getAttribute("id")).intValue(),
			"abcd", ((Long)session.getAttribute("expiration")).longValue(), 
			((Long)session.getAttribute("current")).longValue(), ((Integer)userId).intValue(), friend).getPosts();
%>
<p>
<%
	if (posts.size() == 0) {
%>
		This user has no photos.
<%
	}
%>
</p>
<div id="images">
<%
	for (int i=posts.size()-1; i>=0; i--) {
		PostDetailsInfo post = (PostDetailsInfo) posts.get(i);
%>
	<% if (post.getPostPhotoId()!=-1){
		String photoURL = Utils.MAIN_PATH+userId.toString()+"/"+post.getPostPhotoName();
	%>
		<span style="margin: 15px"><%= Utils.aAbsolute(photoURL, Utils.img(photoURL)) %></span>
	<% } %>
<%
	}
%>
</div>