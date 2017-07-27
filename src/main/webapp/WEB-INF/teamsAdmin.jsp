<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Beginning of Days</title>
</head>
<body>
	<h1>Welcome Iluvatar!</h1>
	<br>
	<form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout" />
    </form>
	<table border=1>
		<tr>
			<th>Name</th>
			<th>Guild</th>
			<th>Age</th>
			<th>Action</th>
		</tr>
		<c:forEach items="${users}" var="userVar">
		<tr>
			<td><c:out value="${userVar.username}"/></td>
			<td><c:forEach items="${userVar.teams}" var="team">
				<a href ="/admin/${user.id}/guilds/${team.id}"><c:out value="${team.guild}"/></a>
			</c:forEach></td>
			<td><c:out value="${userVar.created_at}"/></td>
			<td><a href="/admin/${admin.id}/delete/${userVar.id}">Destroy</a><a href ="/admin/${admin.id}/makeAdmin/${userVar.id}">Make User Admin</a></td>
		</tr>
		</c:forEach>
	</table>
	<br>
	<p>${message}</p>
	<br>
	<form method = "post" action="/admin/${user.id}/addToTeam">
	<p>Name:
		<select name="username">
   			<c:forEach items="${users}" var="user">
   				<option value="${user.id}"> ${user.username}</option>
   			</c:forEach>
   		</select>
   	</p>
   	<p>Guild:
		<select name="guild">
   			<c:forEach items="${teams}" var="team">
   				<option value="${team.id}"> ${team.guild}</option>
   			</c:forEach>
   		</select>
   	</p>
   	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
   	<input type="submit" value="Join"/>
   	</form>
   	
   	<h3>Create Team</h3>
   	<br>
   	<form:form method="post" action="/admin/${user.id}/makeTeam" modelAttribute="team">
		 <p>
            <label for="guild">Team Name:</label>
            <input type="text" id="guild" name="guild"/>
        </p>
        <p>
            <label for="size">Team Size:</label>
            <input type="text" id="size" name="size"/>
        </p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Create"/>
    </form:form>
   	
	
	
	

</body>
</html>