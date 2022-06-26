<%@page import="java.util.Collection"%>
<%@page import="hr.fer.zemris.java.p12.model.Polls"%>
<%@page import="hr.fer.zemris.java.p12.Kljucevi"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Index</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
</head>
<body>
 	<h1>Dostupne su sljedeće ankete</h1>
 	<ol>
 		<% for(Polls poll : ((Map<Long,Polls>) request.getAttribute(Kljucevi.MAPA_POLLS)).values()) { %>
 			<h2><%=poll.getTitle() %></h2>
 			<p><%=poll.getMessage()%></p>
 			<a href="/voting-app/servleti/glasanje?pollID=<%=poll.getId()%>">link</a>
 		<%} %>
 	</ol>
</body>
</html>