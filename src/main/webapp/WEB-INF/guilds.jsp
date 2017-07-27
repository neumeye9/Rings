<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 	<h1> Fellowship of the Dojo</h1>
 	<br>
 	<h2><c:out value="${team.guild}"/></h2>
 	<br>
	<h2>Team Status : <c:out value="${team.users.size()}"/> / <c:out value="${team.getSize()}"/></h2> ${full}
	<table>
		<tr>
			<th>Name</th>
			<th>Age</th>
			<th>Action</th>
		</tr>
		<c:forEach items="${team.users}" var="user">
		<tr>
			<td><c:out value="${user.username}"/></td>
			<td><c:out value="${user.created_at}"/></td>
			<td><a href="/admin/${admin.id}/destroy/${user.id}">Destroy</a> / <a href ="/admin/${admin.id}/update/${user.id}/onTeam/${team.id}">Update</a></td>
		</tr>
		</c:forEach>
	</table>
	
	<a href="/admin/${admin.id}/createTeam">Back</a>
</body>
</html>