<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Index</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
</head>
<body>
	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>
	<p><img src="<%out.print(response.encodeURL(request.getContextPath()+ "/reportImage")); %>" alt="" /></p>
</body>
</html>