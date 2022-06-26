<%@page import="hr.fer.oprpp2.hw04.Kljucevi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Index</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
</head>
<body>
	<%for(String color : Kljucevi.BG_COLORS){ %>
		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/setcolor?"+Kljucevi.KEY_USER_COLOR+"=" + color)); %>><%=color.toUpperCase() %></a>
	<%} %>
</body>
</html>