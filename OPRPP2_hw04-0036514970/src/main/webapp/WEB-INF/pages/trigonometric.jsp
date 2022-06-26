<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.oprpp2.hw04.Kljucevi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Tablica trigonometrije</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
</head>
	<body>
		<h3>Tablica trigonometrije</h3>
		<table style="border-collapse: collapse; width: 100%;" border="1">
			<tbody>
				<tr><th>Broj</th><th>Sinus</th><th>Kosinus</th></tr>
				<%
				for(Map<String,Integer> map : (List<Map<String,Integer>>)request.getAttribute(Kljucevi.TRIGONOMETRIC_MAP)){%>
					<tr><th><%=map.get("kut") %></th><th><%=map.get("sin") %></th><th><%=map.get("cos") %></th></tr>		
				<%} %>
			</tbody>
		</table>
	</body>
</html>