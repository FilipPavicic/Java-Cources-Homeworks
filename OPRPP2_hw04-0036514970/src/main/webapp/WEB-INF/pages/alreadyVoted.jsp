<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<title>Index</title>
<link rel="stylesheet" href=<%out.print(response.encodeURL(request.getContextPath()+ "/pages/css.jsp")); %>>
</head>
<body>
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Vaš glas je već zabilježen, rezultat glasanja možete pogledati ovdje <a href=<%out.print(response.encodeURL(request.getContextPath()+ "/glasanje-rezultati")); %>>rezultat</a></p>
</body>
</html>