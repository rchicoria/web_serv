<%@ page import="phasebook.controller.*" %>
<%@ page import="phasebook.user.*" %>

<%
	String title = session.getAttribute("title").toString();
	session.removeAttribute("title");
	String url = session.getAttribute("url").toString();
	session.removeAttribute("url");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="public/css/style.css" rel="stylesheet" type="text/css">
	<link href="public/css/MooFlow.css" rel="stylesheet" type="text/css">
	<script src="public/js/action.js"></script>
	<script type="text/javascript" src="http://gettopup.com/releases/latest/top_up-min.js"></script>
	<script type="text/javascript" src="path/to/top_up-min.js?fast_mode=1"></script>
	<script type="text/javascript">
	  TopUp.addPresets({
	    "#images a": {
	      group: "images",
	      layout: "flatlook",
	      shaded: 1,
	      overlayClose: 1
	    }
	  });
	</script> 
	<title>Phasebook - <%= title %></title>
</head>
<body>
	<div id="header">
		<table id="headercontainer">
			<tr>
				<td width="150">
					<%= Utils.a("", "<h1>Phasebook</h1>") %>
				</td>
				<% if (session.getAttribute("id") != null) { %>
				<td>
					<form action="<%= Utils.url("") %>" method="get" style="display:inline">
						<input type="text" name="search" value="<%= Utils.text(request.getParameter("search")) %>">
						<input type="submit" value="Search">
					</form>
				</td>
				<td id="menupopupheader" width="150" style="text-align:right; color:white" onmouseover="showPopup()" onmouseout="hidePopup()">
					<%
						String notifications = "";
						if (Utils.getNumberNotifications(Integer.parseInt(session.getAttribute("id").toString()),
								session.getAttribute("id"), session.getAttribute("password"))>0)
							notifications = " (" + Utils.getNumberNotifications(Integer.parseInt(session.getAttribute("id").toString()),
									session.getAttribute("id"), session.getAttribute("password")) + ")";
					%>
					<%= Utils.text(Utils.getUserBean().getUserById(session.getAttribute("id"),
							session.getAttribute("id"), session.getAttribute("password")).getName() + notifications) %>
					<div id="menupopup">
						<ul>
							<li>
								<%= Utils.a("", "My profile") %>
							</li>
							<li>
								<%= Utils.a("edit", "Edit Account") %>
							</li>
							<li>
								<%= Utils.a("notifications", "Notifications" + notifications) %>
							</li>
							<li>
								<%= Utils.a("lottery", "Lottery ("+Utils.getUserBean().getUserById(session.getAttribute("id"),
										session.getAttribute("id"), session.getAttribute("password")).getMoney()+" L&euro;)") %>
							</li>
							<li>
								<%= Utils.a("logout", "Logout") %>
							</li>
						</ul>
					</div>
					<script>
						document.getElementById("menupopup").style.display = 'none';
					</script>
				</td>
				<% } %>
			</tr>
		</table>
	</div>
	<div id="container">
		<% pageContext.include("/WEB-INF/" + url); %>
	</div>
</body>
</html>