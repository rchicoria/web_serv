<%@ page import="javax.naming.*" %>
<%@page import="controller.*"%>

<h1>Login</h1>

<%
if (session.getAttribute("error") != null)
	{
%>
		<p style="color:red"><%= session.getAttribute("error") %></p>
<%
		session.removeAttribute("error");
	}
%>

<form method="POST" action="LoginUser">
	<table>
		<tr>
			<td class="label">Email</td>
			<td><input type="text" name="email"></td>
		</tr>
		<tr>
			<td class="label">Password</td>
			<td><input type="password" name="password"></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Login" name="B1"></td>
		</tr>
	</table>
	
</form>