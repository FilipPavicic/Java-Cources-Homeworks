	<%@page import="hr.fer.zemris.java.tecaj_13.web.servlets.Kljucevi"%>
<h2>
	Trenutni korisnik:
	<%
		if(Boolean.parseBoolean(request.getParameter("is_user_logged"))) {
			String firstName = (String) session.getAttribute(Kljucevi.CURRENT_USER_FN);
			String lastName = (String) session.getAttribute(Kljucevi.CURRENT_USER_LN);
			out.print(firstName + " " + lastName);
	%>
</h2>
<a href="<%=response.encodeURL(request.getContextPath()+ "/servleti/logout")%>"> 
	<input style="float: right;" type="button" value="Logout" />
</a>
	<%
		}else {
			out.print("not logged in");
		
	%>
</h2>
	<%
		}
	%>