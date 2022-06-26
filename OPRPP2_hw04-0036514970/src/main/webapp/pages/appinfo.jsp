<%@page import="hr.fer.oprpp2.hw04.Utils"%>
<%@page import="hr.fer.oprpp2.hw04.Kljucevi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Index</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
</head>
<body>
	<P> Vrijeme rada web aplikacije je: <%=Utils.runningTime((Long)pageContext.getServletContext().getAttribute(Kljucevi.RUNNING_TIME)) %></P>
</body>
</html>