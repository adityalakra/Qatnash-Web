<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Mark Attendance</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		
		
		<meta name="google-signin-client_id" content="627323629975-u2ufr9pe40kdkn50hl2r4q2b34bjnke2.apps.googleusercontent.com">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
					 <script>
						    function signOut() {
						    	
						    	console.log('sign out.');
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
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		
		<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/skel.css"/>'/>
		<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
		<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style-xlarge.css"/>'/>
		
		<script type='text/javascript' src='<c:url value="/resources/js/jquery.min.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/resources/js/skel.min.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/resources/js/skel-layers.min.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/resources/js/init.js"/>'></script>
	</head>
	
	<body>
		<!-- Header -->
			<header id="header">
				<h1><a href="index.html">Attendance Portal</a></h1>
				<nav id="nav">
					<ul>
						<li><a href="/Attendance/markAttendance">Mark Attendance</a></li>
						<li><a href="/Attendance/studentProfile">Student Profile</a></li>
						<li><a href="/Attendance/updateProfile">Update Profile</a></li>
						<li><a href="/Attendance/markAttendance"  onclick="signOut();">Sign out</a></li><!-- this is the sign out link -->
					</ul>
				</nav>
			</header>
		<section id="main" class="wrapper">
				<div class="container">
				<h2 class="align-center">Student Profile</h2><hr>
				<section>
				<div class="row">
				<div class ="col-md-4"><img src=${Image} height="100px" width="100px" /></div>
				<div class="col-md-4"><h4>${studentName}</h4></div>
				<div class="col-md-4">
						<blockquote>
						${emailID}<br>
						Semester:${semester}
						</blockquote>
				</div>
				</div>
		</section>
		<!-- Table -->
		  <section>            
		  <div class="table-wrapper">
		  <table>
		    <thead>
		      <tr>
		        <th>No.</th>
		        <th>Course</th>
		        <th>Total Attendance</th>
				<th>Last Updated On</th>
		      </tr>
		    </thead>
		    <tbody>
		     <c:forEach var="attendance" items="${attendanceList}" varStatus="loop">
					 <tr>
					 	<td>${loop.index + 1}</td>
					    <td>${attendance.courseName}</td>
					    <td>${attendance.totalAttendance}</td>		
					    <td>${lastAttendanceDates[loop.index]}</td>
					  </tr>
					
					</c:forEach>
		    </tbody>
		  </table>
		  </div>
		</section>
		</div>
		</section>
		
	</body>
</html>