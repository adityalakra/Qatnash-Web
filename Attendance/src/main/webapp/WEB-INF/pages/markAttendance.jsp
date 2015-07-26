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
		<title> Attendance </title>
		<style type = "text/css">
		select {
			font-size: 20px;
			font-family: 'Trebuchet MS';
		}	​
		button{
			font-size: 20px;
			font-family: 'Trebuchet MS';
		}
		#mydropdown0{
			width:180px;   
		}
		#mydropdown1{
			width:180px;   
		}
		#mydropdown2{
			width:90px;   
		}
		#mydropdown3{
			width:90px;   
		}
		
		</style>
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
				<li class="active"><a href="/Attendance/markAttendance">Mark Attendance</a></li>
				<li><a href="/Attendance/studentProfile">Student Profile</a></li>
				<li><a href="/Attendance/updateProfile">Update Profile</a></li>
				<li><a href="#" onclick="signOut();">Sign out</a></li><!-- this is the sign out link -->
			</ul>
		</div>
		<h1>${message}</h1>
		
		<form name="attendance" action="markAttendance/save" method="POST">
			<div align="center">
				<h3> Select the Course </h3>
				<select name="subject" id = "mydropdown0">
				<c:forEach var="subjects" items="${subjectsList}">
				<option value=${subjects.courseCode}>${subjects.courseName}</option>
				</c:forEach>
				<!--<option selected="true" style="display:none;">Course</option>
				<option value="AP">AP</option>
				<option value="OS">OS</option>
				<option value="ADA">ADA</option>
				<option value="ADA">EVS</option> -->
				</select>
				
				<br>
				
				<h3> Date of the Class </h3>
				<select name="d" id = "mydropdown1">
					<option value=1 selected>${d1}</option>
					<option value=2>${d2}</option>
				<!-- <option selected="true" style="display:none;">Date</option>
				<option value="June, 25th 2015">June, 25th 2015</option>
				<option value="June, 24th 2015">June, 24th 2015</option>
				<option value="June, 23th 2015">June, 23th 2015</option> -->
				</select>

				<h3> Seat Number </h3>
					<div align="centre">
						<select name="rowNo" id = "mydropdown2">
							<option selected="true" style="display:none;">Row</option>
							<option value="A">A</option>
							<option value="B">B</option>
							<option value="C">C</option>
						</select>
						<select name="seatNo" id = "mydropdown3">
							<option selected="true" style="display:none;">Column</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
						</select>
					</div>
				<br>
				<button type="submit" body style = 'font-family: "Trebuchet MS"; font-size: 20px'  >Submit</button>
			</div>
		</form>
	</body>
</html>