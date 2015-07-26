<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<h2>Here is a simple CRUD using Spring MVC and MongoDB.</h2>

		<form action="person/save" method="post">
			<input type="hidden" name="_id">
			<label for="name">Person Name</label>
			<input type="text" id="name" name="name"/>
			<input type="submit" value="Submit"/>
		</form>

	<table border="1">
		<c:forEach var="person" items="${personList}">
			<tr>
				<td>${person.name}</td><td><input type="button" value="delete" onclick="window.location='person/delete?id=${person.name}'"/></td>
			</tr>
		</c:forEach>
	</table>	
</body>
</html>