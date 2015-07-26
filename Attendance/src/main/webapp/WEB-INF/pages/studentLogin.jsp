<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Login</title>
<meta name="google-signin-client_id" content="627323629975-u2ufr9pe40kdkn50hl2r4q2b34bjnke2.apps.googleusercontent.com">
<script src="https://apis.google.com/js/platform.js" async defer></script>

<script type="text/javascript">
function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  var id_token = googleUser.getAuthResponse().id_token;
  console.log('ID Token: ' + id_token);
  console.log('ID: ' + profile.getId());
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail());
  
  window.location = '/Attendance/student/login?userID=' + profile.getId() + '&emailID=' + profile.getEmail() + '&name=' + profile.getName();
}
</script>
</head>
<body>
	<div></div>
	<div class="g-signin2" data-onsuccess="onSignIn"></div>
	
	<!-- <h2>Sign In</h2>
	<form action="student/login" method="get">
	<fieldset>
	StudentID: <br>
	<input type="text" name="userID">
	<input type="submit" name="submit">
	</fieldset>
	</form>
	
	
	<h2>Student Login Page</h2>
	
	<form action="student/save" method="post">
	<fieldset>
	<legend>Student Information</legend>
	<input type="hidden" name="_id">
		Student Name:<br>
		<input type="text" name="name"/>
		<br>Email Id:<br>
		<input type="text" name="emailID"/>
		<br>User Id:<br>
		<input type="text" name="userID"/>
		<input type="submit" value="Submit"/>
	</fieldset>
	</form> -->
	
	<%-- <form action="student/update" method="post">
	<fieldset>
	<legend>Update Information</legend>
	Enter Semester:<br>
	<input type="text" name="semester">
	<br>Select Subject1<br>
	<select name="Subject1">
	<c:forEach var="subjects" items="${subjectsList}">
	<option value=${subjects.courseCode}>${subjects.courseName}</option>
	</c:forEach>
	</select>
	<br>Select Subject2<br>
	<select name="Subject2">
	<c:forEach var="subjects" items="${subjectsList}">
	<option value=${subjects.courseCode}>${subjects.courseName}</option>
	</c:forEach>
	</select>
	<input type="submit" value="Submit">
	</fieldset>
	</form>
 --%>
</body>
</html>