<%@ page import="phasebook.controller.*"%>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.post.*" %>
<%@ page import="java.util.*" %>

<%
	PhasebookUserRemote userBean = Utils.getUserBean();
	PhasebookUser user;
	Object userId;
	
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
%>

<h1>Edit Account</h1>

<%
	if (session.getAttribute("error") != null)
	{
%>
		<p style="color:red"><%= session.getAttribute("error") %></p>
<%
		session.removeAttribute("error");
	}
%>

<form method="POST" action="EditAccountForm">
	<table>
		<tr>
			<td class="label">Name</td>
			<td><input type="text" name="name" value="<%= user.getName() %>"></td>
		</tr>
		<tr>
			<td class="label">Password</td>
			<td><input type="password" name="password1"></td>
		</tr>
		<tr>
			<td class="label">Repeat password</td>
			<td><input type="password" name="password2"></td>
		</tr>
		<tr>
			<td class="label">Profile picture</td>
			<td>
				<div style="width: 200px; height: 250px; overflow: auto; border: 1px solid black; padding: 6px">
					<p><input type="radio" name="avatar" id="0" value="0" <% if (user.getPhoto()==null) { %>checked<% } %>> <label for="0">No photo</label></p>
					<%
						List<Post> posts = userBean.getUserReceivedPosts(userId,
								session.getAttribute("id"), session.getAttribute("password"));
						for (int i=posts.size()-1; i>=0; i--) {
							if (posts.get(i).getPhoto()!=null && posts.get(i).getDeletedAt()==null) {
								String photoURL = Utils.MAIN_PATH+userId.toString()+"/"+posts.get(i).getPhoto().getName();
								int photoID = posts.get(i).getPhoto().getId();
					%>
								<p>
									<input type="radio" name="avatar" id="<%= photoID %>" value="<%= photoID %>" <% if (user.getPhoto() != null && user.getPhoto().getId()==photoID) { %>checked<% } %>>
									<label for="<%= photoID %>"><%= Utils.img(photoURL) %></label>
								</p>
					<%
							}
						}
					%>
				</div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Save" name="B1"></td>
		</tr>
	</table>
</form>