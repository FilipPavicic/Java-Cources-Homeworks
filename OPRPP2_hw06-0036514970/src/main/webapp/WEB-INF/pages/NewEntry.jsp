<%@page import="hr.fer.zemris.java.tecaj_13.model.User"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.zemris.java.tecaj_13.web.servlets.Kljucevi"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	boolean isUserLogged = (boolean) request.getAttribute(Kljucevi.IS_USER_LOGGED);
	String nick = (String) request.getAttribute(Kljucevi.NICK);
	Map<String,String> errors = null;
	String tittle  = (String) request.getAttribute(Kljucevi.TITLE);
	String text = (String) request.getAttribute(Kljucevi.TEXT);
	Boolean hasErorrs = (Boolean) request.getAttribute(Kljucevi.HAS_ERRORS);
	if(hasErorrs != null && hasErorrs) {
		errors = (Map<String,String>) request.getAttribute(Kljucevi.ERRORS);
		tittle = (String) request.getAttribute(Kljucevi.TITLE);
		text = (String) request.getAttribute(Kljucevi.TEXT);
	}
	Long entryBlogId = (Long) request.getAttribute(Kljucevi.BLOG_ENTRY_ID);
	String job = entryBlogId == null ? "/new" : "/edit/" + entryBlogId;
/* 	if(isUserLogged) {
		HttpSession session = request.getSession();
		long userID = (long) session.getAttribute(Kljucevi.CURRENT_USER_ID);
		String firstName = (String) session.getAttribute(Kljucevi.CURRENT_USER_FN);
		String lastName = (String) session.getAttribute(Kljucevi.CURRENT_USER_LN);
		String nick = (String) session.getAttribute(Kljucevi.CURRENT_USER_NICK);
	} */
%>
<html>
<body>
	<jsp:include page="Header.jsp" >
	  <jsp:param name="is_user_logged" value="<%=isUserLogged%>" />
	</jsp:include>
	<h2><%out.print(entryBlogId == null ? "Dodavanje novog" : "Uređivanje");%>  bloga</h2>
	<form action="<%=response.encodeURL(request.getContextPath()+ "/servleti/author/" + nick + job)%>" method=POST accept-charset="UTF-8">
		<label >Title:</label><br> 
		<textarea  cols="50" name="<%=Kljucevi.TITLE%>" rows="1" maxlength="200"><%if(tittle!=null) out.print(tittle);%></textarea>
		<%if(errors != null && errors.containsKey(Kljucevi.NICK)) {%>
			<label style="color: red;"><%=errors.get(Kljucevi.NICK) %></label><br />
		<%} %>  
		<br>
		<label >Text:</label><br> 
		<textarea  cols="50" name="<%=Kljucevi.TEXT%>" rows="4" maxlength="4096"><%if(text!=null) out.print(text);%></textarea>
		<%if(errors != null && errors.containsKey(Kljucevi.NICK)) {%>
			<label style="color: red;"><%=errors.get(Kljucevi.NICK) %></label><br />
		<%} %>  
		<br><br>
		<input type="submit" value="Submit" />
	</form>
</body>
</html>
