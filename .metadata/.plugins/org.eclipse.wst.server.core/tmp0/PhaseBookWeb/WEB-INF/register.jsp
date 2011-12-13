<%@page import="phasebook.controller.*"%>
<%@ page import="javax.naming.*" %>
<%@ page import="phasebook.user.*" %>

<h1>Register</h1>

<%
	String name = "";
	String email = "";
	if (session.getAttribute("name") != null)
	{
		name = session.getAttribute("name").toString();
		session.removeAttribute("name");
	}
	if (session.getAttribute("email") != null)
	{
		email = session.getAttribute("email").toString();
		session.removeAttribute("email");
	}

	if (session.getAttribute("error") != null)
	{
%>
		<p style="color:red"><%= session.getAttribute("error") %></p>
<%
		session.removeAttribute("error");
	}
%>

<form method="POST" action="CreateUserForm">

	<table>
		<tr>
			<td class="label">Name</td>
			<td class="label"><input type="text" name="name" value="<%= Utils.text(name) %>"></td>
		</tr>
		<tr>
			<td class="label">Email</td>
			<td><input type="text" name="email" value="<%= Utils.text(email) %>"></td>
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
			<td></td>
			<td><input type="submit" value="Register" name="B1"></td>
		</tr>
	</table>
	
</form>

<p class="tip">
	Already a user? <%= Utils.a("login", "Login") %> with your account
</p>