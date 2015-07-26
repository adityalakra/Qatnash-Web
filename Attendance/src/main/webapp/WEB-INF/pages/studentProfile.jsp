<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<title>Attendance Portal</title>
		
		<!-- this is the javascript for the sign out link -->
		<meta name="google-signin-client_id" content="627323629975-u2ufr9pe40kdkn50hl2r4q2b34bjnke2.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
		 <script>
		    function signOut() {
		      var auth2 = gapi.auth2.getAuthInstance();
		      auth2.signOut().then(function () {
		        console.log('User signed out.');
		        window.location = "sessionlogout";
		      });
		    }
		
		    function onLoad() {
		      gapi.load('auth2', function() {
		        gapi.auth2.init();
		      });
		    }
		  </script>
		
	</head>
	<body style = 'font-family: "Trebuchet MS"'>
		<center><h1> Attendance Portal </h1></center>
		<hr/>
		<div class="container" body style = 'font-family: "Trebuchet MS"; font-size: 20px' >
			<ul class="nav nav-pills">
				<li><a href="/Attendance/markAttendance">Mark Attendance</a></li>
				<li class="active"><a href="/Attendance/studentProfile">Student Profile</a></li>
				<li><a href="/Attendance/updateProfile">Update Profile</a></li>
				<li><a href="#" onclick="signOut();">Sign out</a></li><!-- this is the sign out link -->
			</ul>
		</div>
		<table style="width:100%">
			<tr>
			 	<td><h3>Name:${studentName}</h3></td>
			 	<td><h3>Email ID:${emailID}</h3></td>		
			    <td><h3>Semester:${semester}</h3></td>
			    <%-- <td><h3>Roll Number:${userID}</h3></td> --%>
			    
			  </tr>
		</table>
		<h3>
		<h3>
		<h3>Present Attendance<h3>
		<table style="width:100%">
			<tr>
			 	<td><b>No.</b></td>
			    <td><b>Course</b></td>
			    <td><b>Total Attendance</b></td>		
			    <td><b>Last Attendance Date</b></td>
			  </tr>
			<c:forEach var="attendance" items="${attendanceList}" varStatus="loop">
			 <tr>
			 	<td>${loop.index + 1}</td>
			    <td>${attendance.courseName}</td>
			    <td>${attendance.totalAttendance}</td>		
			    <td>${lastAttendanceDates[loop.index]}</td>
			  </tr>
			
			</c:forEach>
		</table>
		
	</body>
</html>