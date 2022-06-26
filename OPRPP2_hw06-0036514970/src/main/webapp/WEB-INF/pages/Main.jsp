<%@page import="hr.fer.zemris.java.tecaj_13.model.User"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.zemris.java.tecaj_13.web.servlets.Kljucevi"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	boolean isUserLogged = (boolean) request.getAttribute(Kljucevi.IS_USER_LOGGED);
	Map<String,String> errors = null;
	String nick = null;
	Boolean loginIncorrect = null;
	Boolean hasErorrs = (Boolean) request.getAttribute(Kljucevi.HAS_ERRORS);
	List<User> users = (List<User>) request.getAttribute(Kljucevi.USERS);
	if(hasErorrs != null && hasErorrs) {
		errors = (Map<String,String>) request.getAttribute(Kljucevi.ERRORS);
		nick = (String) request.getAttribute(Kljucevi.NICK);
		loginIncorrect = (Boolean) request.getAttribute(Kljucevi.LOGIN_INCORRECT);
	}
/* 	if(isUserLogged) {
		HttpSession session = request.getSession();
		long userID = (long) session.getAttribute(Kljucevi.CURRENT_USER_ID);
		String firstName = (String) session.getAttribute(Kljucevi.CURRENT_USER_FN);
		String lastName = (String) session.getAttribute(Kljucevi.CURRENT_USER_LN);
		String nick = (String) session.getAttribute(Kljucevi.CURRENT_USER_NICK);
	} */
%>
<html>
<head>
	 <script>
       	function Statistika() {
       		const xhttp = new XMLHttpRequest();
       		xhttp.onload = function() {
       			var HTML = "<table class='table table-striped table-bordered table-hover' style='width:500px'>";

       		    HTML += "<tr><th>Student</th><th>Broj Blogova</th></tr>";

       		    var data = JSON.parse(data.responseText);
       		    for(var i = 0;i<data.length;i++){
       		      HTML += "<tr><td>" + data[i].student + "</td><td>" + data[i].brojBlogova + "</td></tr>";
       		    }

       		    HTML += "</table>";
       		 	document.getElementById('tablica').innerHTML = HTML;
       		    }
       		
       		xhttp.open("GET", "statistika", true);
       		xhttp.send();
		}
    </script>
</head>
<body>
	<jsp:include page="Header.jsp" >
	  <jsp:param name="is_user_logged" value="<%=isUserLogged%>" />
	</jsp:include>
	<%
		if(!isUserLogged) {
			if(loginIncorrect != null && loginIncorrect) {%>
					<label style="color: red;">The nick name or password is incorrect</label><br />
			<%} %>
			<form action="<%=response.encodeURL(request.getContextPath()+ "/servleti/main")%>" method=POST accept-charset="UTF-8">
				<label>Nick:</label><br /> 
				<input name="<%=Kljucevi.NICK%>" type="text" value="<%if(nick!=null) out.print(nick);%>"/><br />
				<%if(errors != null && errors.containsKey(Kljucevi.NICK)) {%>
					<label style="color: red;"><%=errors.get(Kljucevi.NICK) %></label><br />
				<%} %>  
				
				<label>Password:</label><br /> 
				<input name="<%=Kljucevi.PASSWORD%>" type="password" /><br />
				<%if(errors != null && errors.containsKey(Kljucevi.PASSWORD)) {%>
					<label style="color: red;"><%=errors.get(Kljucevi.PASSWORD) %></label><br />
				<%} %> 
				
				<br /> 
				<input type="submit" value="Login" /> <a href="<%=response.encodeURL(request.getContextPath()+ "/servleti/signin")%>"> <input
					 type="button" value="Sign in" />
				</a>
			</form>
	<%
		}
	%>
	<h3>Blogovi korisnika</h3>
	<ul>
		<%for(User user: users) {%>
			<li><a href="<%=response.encodeURL(request.getContextPath()+ "/servleti/author/" + user.getNick())%>"><%=user.getFirstName() + " " + user.getLastName() %></a></li>
		<%} %>
	</ul>
	<input type="submit" value="Login" /> 
	<a href="javascript:void(0);" onclick="Statistika();"> 
	<input type="button" value="Statistika" />
	</a>
	<div id="tablica"></div>
</body>
</html>
