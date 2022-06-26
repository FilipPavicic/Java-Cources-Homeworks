<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.oprpp2.hw04.Kljucevi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Index</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
<style type="text/css">table.rez td {text-align: center;}</style>
</head>
<body>
	<h1>Rezultati glasanja</h1>
 	<p>Ovo su rezultati glasanja.</p>
 	<table border="1" cellspacing="0" class="rez">
 		<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
 		<tbody>
 			<%
 			Map<String,Map<String,String>> mapa_bendova = (Map<String,Map<String,String>>)request.getAttribute(Kljucevi.MAPA_BENDOVA);
 			List<Map<String,String>> lista_rez = (List<Map<String,String>>)request.getAttribute(Kljucevi.LISTA_BENDOVA_REZ);
 			Collections.sort(lista_rez, ((o1,o2) -> Integer.valueOf(o2.get("rezultat")).compareTo(Integer.valueOf(o1.get("rezultat")))));
 			int best_rez = Integer.parseInt(lista_rez.get(0).get("rezultat"));
 			for(Map<String,String> el : lista_rez) {
 			%>
 				<tr><td><%=mapa_bendova.get(el.get("id")).get("bend")%></td><td><%=el.get("rezultat") %></td></tr>
 			<%} %>	
 		</tbody>
 	</table>
 	<p><img src="<%out.print(response.encodeURL(request.getContextPath()+ "/glasanje-grafika")); %>" alt="" /></p>	
 	<p>
  		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/glasanje-xls")); %>>Export to Xls</a>
  	</p>
  	<p>U Nastavku se nalaze linkovi najpopularijih bendova</p>
  	<%
  		for(Map<String,String> el : lista_rez) {
  			if(Integer.parseInt(el.get("rezultat")) == best_rez) { %>
  				<a href=<%=mapa_bendova.get(el.get("id")).get("link")%>><%= mapa_bendova.get(el.get("id")).get("bend")%></a><br>
  		<% }
  		}
			
  	%>
  			
</body>
</html>