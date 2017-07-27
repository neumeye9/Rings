<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
    <h1>Welcome, <c:out value="${admin.username}"></c:out>!</h1>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout" />
    </form>
    <p> Welcome to your awesome magical ring finder, put the ring on, only good things will happen. 
    Maybe it'll make you live forever, go invisible, turn your inherent hunger for riches or power 
    into an insatiable curse that eventually dooms your entire species.</p>
    <br>
    <br>
    <a href="/admin/${admin.id}/createRing">Ring Creator (Powerful Ainur only)</a>
    <br>
    <a href = "/admin/${admin.id}/createTeam">Person/Team Creator (Powerful Ainur only)</a>
    <br>
    <br>
    <form method = "post" action="/selectRing/${admin_id}">
   	<p>
   		<select name="name">
   			<c:forEach items="${rings}" var="ring">
   				<option value="${ring.id}"> ${ring.name}</option>
   			</c:forEach>
   		</select>
   	</p>
   	<p>
   		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
   		<input type="submit" value="BIND YOURSELF IN DARKNESS"></input>
   	</p>
   	</form>
   	<table border=1>
   		<tr>
   			<th>Rings you have found</th>
   			<th>Action</th>
   		</tr>
   		<c:forEach items="${user.rings}" var="ring">
   		<tr>
   			<td><c:out value="${ring.name}"/></td>
   			<td><a href="/destroy/${ring.id}">Lost the Ring (Delete)</a></td>
   		</tr>
   		</c:forEach>
   	</table>
</body>
</html>