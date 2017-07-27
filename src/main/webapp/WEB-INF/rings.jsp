<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create a Ring of Power</title>
</head>
<body>
	<h1>Forge ring of power</h1>
	<br>
	<p><form:errors path="user.*"/></p>
	<form:form method="post" action="/admin/${user.id}/ring" modelAttribute="ring">
		 <p>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name"/>
        </p>
        <input type="submit" value="Create"/>
    </form:form>
    <br>
    <a href="/admin">Back</a>
</body>
</html>