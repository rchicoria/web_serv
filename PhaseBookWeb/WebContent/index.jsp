<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="phasebook.controller.*"%>

<%
	
	/*
	*	Controller do web tier
	*	Todas as páginas passam por aqui primeiro. O controller analiza o URL
	*	e carrega os elementos da página de acordo.
	*/
	
	String title = "Login";
	String url   = "login.jsp";
	String p     = request.getParameter("p");
	if (p == null)
		p = "";

	if (p.compareTo("logout") == 0 && session.getAttribute("id") != null)
	{
		session.removeAttribute("id");
		title = "Login";
		url   = "login.jsp";
	}
	
	else if (p.compareTo("register") == 0 && session.getAttribute("id") == null)
	{
		title = "Register";
		url   = "register.jsp";
	}
	
	else if (p.compareTo("login") == 0 && session.getAttribute("id") == null)
	{
		title = "Login";
		url   = "login.jsp";
	}
	
	else if (request.getParameter("search") != null && session.getAttribute("id") != null)
	{
		title = "Search";
		url   = "search.jsp";
	}
	
	else if (p.compareTo("user") == 0 && session.getAttribute("id") != null && request.getParameter("id") != null)
	{
		try {
			title = Utils.getUserBean().getUserById(request.getParameter("id"),session.getAttribute("id"), session.getAttribute("password")).getName();
		} catch (Exception e) {
			title = Utils.getUserBean().getUserById(session.getAttribute("id"),
					session.getAttribute("id"), session.getAttribute("password")).getName();
		}
		url   = "profile/profile.jsp";
	}
	
	else if (p.compareTo("edit") == 0 && session.getAttribute("id") != null)
	{
		title = "Edit Account";
		url   = "profile/editAccount.jsp";
	}
	
	else if (p.compareTo("notifications") == 0 && session.getAttribute("id") != null)
	{
		title = "Notifications";
		url   = "profile/notification.jsp";
	}
	
	else if (p.compareTo("lottery") == 0 && session.getAttribute("id") != null)
	{
		title = "Charity Lottery";
		url   = "lottery/lottery.jsp";
	}

	// Default
	else
	{
		if(session.getAttribute("id") != null && session.getAttribute("password")!=null){
			title = Utils.getUserBean().getUserById(session.getAttribute("id"),
					session.getAttribute("id"), session.getAttribute("password")).getName();
			url   = "profile/profile.jsp";
		}
		else
		{
			title = "Login";
			url   = "login.jsp";
		}
	}

	session.setAttribute("title", title);
	session.setAttribute("url", url);
	pageContext.include("/WEB-INF/template.jsp");

%>
