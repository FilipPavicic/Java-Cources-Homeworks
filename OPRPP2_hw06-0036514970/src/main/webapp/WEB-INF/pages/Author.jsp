<%@page import="java.util.Arrays"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.User"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.zemris.java.tecaj_13.web.servlets.Kljucevi"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	boolean isUserLogged = (boolean) request.getAttribute(Kljucevi.IS_USER_LOGGED);
	User user = (User) request.getAttribute(Kljucevi.USER);
	String nick = (String) request.getAttribute(Kljucevi.NICK);
	boolean isAuthorLogged = false;
	long current_user_id = 3;
	if(isUserLogged) {
		current_user_id = Long.parseLong((String)session.getAttribute(Kljucevi.CURRENT_USER_ID));
		isAuthorLogged = user.getId() == current_user_id;
	}
%>
<html>
<body>
	<jsp:include page="Header.jsp" >
	  <jsp:param name="is_user_logged" value="<%=isUserLogged%>" />
	</jsp:include>
	<h2>
		Blog korisnika: <%=user.getFirstName() + " " + user.getLastName() %>
	</h2>
	<ul>
		<%for(BlogEntry be : user.getBlogEntries()) { %>
			<li>
				<a href="<%=response.encodeURL(request.getContextPath()+ "/servleti/author/" + nick + "/" +be.getId())%>"><%=be.getTitle()%></a>
				<p>Broj srdaca <%=be.getSrca().size() %></p>
				<%if(isUserLogged && !be.getSrca().stream().mapToLong((u)-> u.getId()).boxed().collect(Collectors.toList()).contains(current_user_id)){ %>
				<a href="<%=response.encodeURL(request.getContextPath()+ "/servleti/addSrce?"+Kljucevi.NICK+"="+nick+"&"+Kljucevi.BLOG_ENTRY_ID+"="+be.getId())%>"> 
					<input  type="button" value="Dodaj srce" />
				</a>
				<%} %>
				<%if(isAuthorLogged){ %>
				<a href="<%=response.encodeURL(request.getContextPath()+ "/servleti/author/" + nick + "/edit/" +be.getId())%>"> 
					<input  type="button" value="Edit" />
				</a>
				<%} %>
			</li><br>
		<%} %>
	</ul>
	<%if(isAuthorLogged){ %>
<a href="<%=response.encodeURL(request.getContextPath()+ "/servleti/author/" + nick + "/new")%>"> 
	<input  type="button" value="Add new blog" />
</a>
	<% }%>
</body>
</html>
