<%@page import="hr.fer.zemris.java.p12.model.PollOptions"%>
<%@page import="java.util.Collection"%>
<%@page import="hr.fer.zemris.java.p12.model.Polls"%>
<%@page import="hr.fer.zemris.java.p12.Kljucevi"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<% Polls poll = (Polls)request.getAttribute(Kljucevi.POLL);%>
<!DOCTYPE html>
<html>
<head>
<title>Index</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
</head>
<body>
 	<h1><%=poll.getTitle()%></h1>
 	<p><%=poll.getMessage()%></p>
 	<ol>
 		<% for(PollOptions option : ((Map<Long,PollOptions>) request.getAttribute(Kljucevi.MAPA_POLLOPTIONS)).values()) { %>
 			<li><a href="glasanje-glasaj?id=<%=poll.getId()%>&pollID=<%=option.getId()%>"><%=option.getOptionTitle()%></a></li>
 		<%} %>
 	</ol>
</body>
</html>