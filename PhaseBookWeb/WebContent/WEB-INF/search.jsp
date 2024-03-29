<%@ page import="phasebook.controller.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="java.util.List" %>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.photo.*" %>

<% 
	PhasebookUserRemote userBean = Utils.getUserBean();
	List<?> users = userBean.getUsersFromSearch(request.getParameter("search"),
			session.getAttribute("id"), session.getAttribute("password"));
%>

<h1>Search users for "<%= Utils.text(request.getParameter("search")) %>"</h1>

<%
	if (users.size() == 0)
	{
%>
		<p>No users were found.</p>
<%
	}
	else for (int i=0; i<users.size(); i++)
	{
		PhasebookUser user = (PhasebookUser) users.get(i);
%>
		<table style="width: 100%">
			<tr>
				<td width="60">
					<% if (user.getPhotoId()!=-1){
						PhotoRemote photoBean = Utils.getPhotoBean();
						String photoURL = Utils.MAIN_PATH + user.getId() + "/"+photoBean.getPhotoById(""+user.getPhotoId(), session.getAttribute("id"), session.getAttribute("password")).getName();
					%>
						<%= Utils.a("user&id="+user.getId(), Utils.smallImg(photoURL)) %>
					<% } %>
				</td>
				<td>
					<b class="user"><%= Utils.a("user&id="+user.getId(), Utils.text(user.getName())) %></b><br />
					<i><%= Utils.text(user.getEmail()) %></i>
				</td>
			</tr>
		</table>
<%
	}
%>