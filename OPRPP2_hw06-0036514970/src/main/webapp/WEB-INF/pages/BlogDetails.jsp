<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogComment"%>
<%@page import="hr.fer.zemris.java.tecaj_13.model.User"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="hr.fer.zemris.java.tecaj_13.web.servlets.Kljucevi"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	boolean isUserLogged = (boolean) request.getAttribute(Kljucevi.IS_USER_LOGGED);
	Map<String,String> errors = null;
	String tittle  = (String) request.getAttribute(Kljucevi.TITLE);
	String text = (String) request.getAttribute(Kljucevi.TEXT);
	List<BlogComment> comments = (List<BlogComment>) request.getAttribute(Kljucevi.COMMENTS);
	String comment = (String) request.getAttribute(Kljucevi.COMMENT);
	Boolean hasErorrs = (Boolean) request.getAttribute(Kljucevi.HAS_ERRORS);
	if(hasErorrs != null && hasErorrs) {
		errors = (Map<String,String>) request.getAttribute(Kljucevi.ERRORS);
	}
	boolean isAuthorLogged = false;
	User user = (User) request.getAttribute(Kljucevi.USER);
	if(isUserLogged) {
		long current_user_id = Long.parseLong((String)session.getAttribute(Kljucevi.CURRENT_USER_ID));
		isAuthorLogged = user.getId() == current_user_id;
	}
	Long entryBlogId = (Long) request.getAttribute(Kljucevi.BLOG_ENTRY_ID);

%>
<html>
<body>
	<jsp:include page="Header.jsp" >
	  <jsp:param name="is_user_logged" value="<%=isUserLogged%>" />
	</jsp:include>
	<h2>Naslov bloga: <%=tittle%></h2>
	<p><%=text %></p>
	<h2>Komentari:</h2>
	<%for(BlogComment comm: comments){ %>
		<p><%out.print(comm.getUsersEMail() +" ["+comm.getPostedOn() +"] : "+comm.getMessage());  %></p>
	<%} %>
	<h2>Ovdje možete ostaviti Vaš komentar</h2>
	<form action="<%=response.encodeURL(request.getContextPath()+ "/servleti/author/" + user.getNick() + "/" +entryBlogId)%>" method=POST accept-charset="UTF-8">
		<label >Dodajte novi kometar:</label><br> 
		<textarea  cols="50" name="<%=Kljucevi.COMMENT%>" rows="2" maxlength="4096"><%if(comment!=null) out.print(comment);%></textarea>
		<%if(errors != null && errors.containsKey(Kljucevi.COMMENT)) {%>
			<label style="color: red;"><%=errors.get(Kljucevi.COMMENT) %></label><br />
		<%} %>  
		<br>
		<br><br>
		<input type="submit" value="Submit" />
	</form>
	<%if(isAuthorLogged){ %>
	<a href="<%=response.encodeURL(request.getContextPath()+ "/servleti/author/" + user.getNick() + "/edit/" +entryBlogId)%>"> 
		<input  type="button" value="Edit" />
	</a>
	<%} %>
</body>
</html>
