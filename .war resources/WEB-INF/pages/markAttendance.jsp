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
		<header id="header">
				<h1>Attendance Portal</h1>
				<nav id="nav">
					<ul>
						<li><a href="/Attendance/markAttendance">Mark Attendance</a></li>
						<li><a href="/Attendance/studentProfile">Student Profile</a></li>
						<li><a href="/Attendance/updateProfile">Update Profile</a></li>
						<li><a href="/Attendance/markAttendance"  onclick="signOut();">Sign out</a></li><!-- this is the sign out link -->
					</ul>
					
				</nav>
		</header>
		
		<div align="center">
		<br>
		<h1><p style="color:red">${message}</p></h1>
		</div>
		
		<!-- Dropdowns-->
		<form action="markAttendance/save" method="post">
		<section id="main" class="wrapper">
		<div style="width:250px; margin:0 auto;">
		<div class="row">
				<div class="col-md-4"><h2 class="align-center">Mark Attendance</h2><hr></div>
				<div class="col-md-4">
				
									<h3>Course</h3>
									<div class="6u 12u$(4)">
										<div class="select-wrapper">
											<select name="subject" id = "mydropdown0"">
											<c:forEach var="subjects" items="${subjectsList}">
											<option value=${subjects.courseCode}>${subjects.courseName}</option>
											</c:forEach>
											</select>
										</div>
									</div><br>
									
								<h3>Seat No</h3>
								<div class="row">
									<div class="4u 12u$(3)">
										<div class="select-wrapper">
											<select name="rowNo" id="rowNo">
												<option value="">- Row -</option>
												<option value="1">A</option>
												<option value="1">B</option>
											</select>
										</div>
									</div>
									<div class="4u 12u$(3)">
										<div class="select-wrapper">
											<select name="seatNo" id="seatNo">
												<option value="">- Column -</option>
												<option value="1">1</option>
												<option value="1">2</option>
											</select>
										</div>
									</div>
									</div>
									<br>
									<div class="6u 12u$(4)">
										<ul class="actions">
											<li><input type="submit" value="Submit" class="special" /></li>
										</ul>
									</div>
								</div>
								<div class="col-md-4"></div>
							</div>
						
				</form>
				</section>
								
	</body>
</html>