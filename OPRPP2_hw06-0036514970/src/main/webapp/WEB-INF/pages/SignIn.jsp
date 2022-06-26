<%@page import="java.util.Map"%>
<%@page import="hr.fer.zemris.java.tecaj_13.web.servlets.Kljucevi"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	Map<String,String> errors = null;
	String firstName = null;
	String lastName = null;
	String email = null;
	String nick = null;
	String password = null;
	Boolean hasErorrs = (Boolean) request.getAttribute(Kljucevi.HAS_ERRORS);
	if(hasErorrs != null && hasErorrs) {
		errors = (Map<String,String>) request.getAttribute(Kljucevi.ERRORS);
		firstName = (String) request.getParameter(Kljucevi.FIRST_NAME);
		lastName = (String) request.getParameter(Kljucevi.LAST_NAME);
		email = (String) request.getParameter(Kljucevi.EMAIL);
		nick = (String) request.getParameter(Kljucevi.NICK);
		password = (String) request.getParameter(Kljucevi.PASSWORD);
	}

%>
<html>
  <body>
	<h2>Registracija</h2>
	<form action="<%=response.encodeURL(request.getContextPath()+ "/servleti/signin")%>" method=POST accept-charset="UTF-8">
		<label>First Name:</label><br />
		<input name="<%=Kljucevi.FIRST_NAME%>" type="text" value="<%if(firstName!=null) out.print(firstName);%>"/><br />
		
		<%if(errors != null && errors.containsKey(Kljucevi.FIRST_NAME)) {%>
			<label style="color: red;"><%=errors.get(Kljucevi.FIRST_NAME) %></label><br />
		<%} %> 
		
		<br />
		<label>Last Name:</label><br />
		<input  name="<%=Kljucevi.LAST_NAME%>" type="text" value="<%if(lastName!=null) out.print(lastName);%>"/><br />
		
		<%if(errors != null && errors.containsKey(Kljucevi.LAST_NAME)) {%>
			<label style="color: red;"><%=errors.get(Kljucevi.LAST_NAME) %></label><br />
		<%} %> 
		
		<br />
		<label>Email:</label><br />
		<input  name="<%=Kljucevi.EMAIL%>" type="text" value="<%if(email!=null) out.print(email);%>"/><br />
		
		<%if(errors != null && errors.containsKey(Kljucevi.EMAIL)) {%>
			<label style="color: red;"><%=errors.get(Kljucevi.EMAIL) %></label><br />
		<%} %>  
		
		<br />
		<label>Nick:</label><br />
		<input  name="<%=Kljucevi.NICK%>" type="text" value="<%if(nick!=null) out.print(nick);%>"/><br />
		
		<%if(errors != null && errors.containsKey(Kljucevi.NICK)) {%>
			<label style="color: red;"><%=errors.get(Kljucevi.NICK) %></label><br />
		<%} %> 
		
		<br />
		<label>Password:</label><br />
		<input  name="<%=Kljucevi.PASSWORD%>" type="password" /><br /> 
		
		<%if(errors != null && errors.containsKey(Kljucevi.PASSWORD)) {%>
			<label style="color: red;"><%=errors.get(Kljucevi.PASSWORD) %></label><br />
		<%} %> 
		
		<br />
		<input type="submit" value="Submit" /></form>
  </body>	
</html>
