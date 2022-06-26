<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Index</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
</head>
<body>
	<p>Background color 
  		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/colors.jsp")); %>>chooser</a>
  	</p>
  	<p>
  		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/trigonometric")); %>>Trigonometrijska tablica</a>
  	</p>
  	<form action="trigonometric" method="GET">
		 Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		 Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		 <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
	</form>
	<p>
  		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/stories/funny.jsp")); %>>Crvenkapica</a>
  	</p>
  	<p>
  		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/report.jsp")); %>>OS usage</a>
  	</p>
  	<p>
  		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/powers?a=1&b=100&n=3 ")); %>>Powers Excel</a>
  	</p>
  	<p>
  		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/appinfo.jsp ")); %>>Vrijeme rada servera</a>
  	</p>
  	<p>Otovreno je glasovanje za najdraži bend
  		<a href=<%out.print(response.encodeURL(request.getContextPath()+ "/glasanje")); %>>anketa</a>
  	</p>
</body>
</html>