<%@page import="hr.fer.zemris.java.p12.model.Polls"%>
<%@page import="hr.fer.zemris.java.p12.Kljucevi"%>
<%@page import="hr.fer.zemris.java.p12.model.PollOptions"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
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

 			for(PollOptions options : ((List<PollOptions>) request.getAttribute(Kljucevi.LIST_POLLOPTIONS))) {
 			%>
 				<tr><td><%=options.getOptionTitle()%></td><td><%=options.getVotesCount()%></td></tr>
 			<%} %>	
 		</tbody>
 	</table>
 	<p><img src="<%out.print(response.encodeURL(request.getContextPath()+ "/servleti/glasanje-grafika?id="+((Polls)request.getAttribute(Kljucevi.POLL)).getId())); %>" alt="" /></p>	
 	<p>
  		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/servleti/glasanje-xls?id="+((Polls)request.getAttribute(Kljucevi.POLL)).getId())); %>>Export to Xls</a>
  	</p>
  	<p>U Nastavku se nalaze linkovi najpopularijih bendova</p>
  	<%
  	int best = -1;
  	for(PollOptions options : ((List<PollOptions>) request.getAttribute(Kljucevi.LIST_POLLOPTIONS))) {
  			if(best == -1) best = options.getVotesCount();
  			if(options.getVotesCount() == best) { %>
  				<a href=<%=options.getOptionLink()%>><%= options.getOptionTitle()%></a><br>
  		<% }
  		}
			
  	%>
  			
</body>
</html>