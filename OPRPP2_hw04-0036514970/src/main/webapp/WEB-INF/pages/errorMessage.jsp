<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Webapp 2</title>
</head>
<body>
	<h1>Dogodila se pogreška!</h1>
	<p><% out.print(request.getAttribute("PORUKA")); %></p>
</body>
</html>