<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.oprpp2.hw04.Kljucevi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Index</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
</head>
<body>
 	<h1>Glasanje za omiljeni bend:</h1>
 	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
 	<ol>
 		<% for(Map<String,String> mapa : (List<Map<String,String>>) request.getAttribute(Kljucevi.LISTA_BENDOVA)) { %>
 			<li><a href="glasanje-glasaj?id=<%=mapa.get("id")%>"><%=mapa.get("bend") %></a></li>
 		<%} %>
 	</ol>
</body>
</html>