<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Dashboard</title>
</head>
<body>
    <h1>Welcome to the Admin Page <c:out value="${currentUser.username}"></c:out></h1>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    <table>
    		<tr>
    			<th>Name</th>
    			<th>Email</th>
    			<th>Action</th>
    		</tr>
    		
    		<c:forEach items="${users}" var="user" varStatus="loop">
    		<tr>
    			<td><c:out value="${user.username}"/></td>
    			<td><c:out value="${user.created_at}"/></td>
    			<td><c:out value="${user.email}"/></td>
  
    		<tr>
    		</c:forEach>
   	 
    </table>
</body>
</html>