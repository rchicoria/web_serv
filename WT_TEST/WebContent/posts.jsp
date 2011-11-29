<%@page import="java.util.HashMap"%>
<%@ page import="client.artefact.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		MethodsService cs = new MethodsService();
		Methods m = cs.getMethodsPort();
		
		out.println(m.getUserPosts(2));
	%>
</body>
</html>