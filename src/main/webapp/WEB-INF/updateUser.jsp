<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1> Cast a spell of changing!</h1>
	<br>
	<br>
	<form method="post" action="/admin/${admin.id}/update/${user.id}/onTeam/${team.id}">
			<input type="text" name="username" placeholder="${user.username}"></input>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="Update"></input>
		</form>

	<a href="/admin/${user.id}/guilds/${team.id}">Back</a>
</body>
</html>