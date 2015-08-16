<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<title>Attendance</title>
		
		<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/skel.css"/>'/>
		<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
		<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style-xlarge.css"/>'/>
		
		<script type='text/javascript' src='<c:url value="/resources/js/jquery.min.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/resources/js/skel.min.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/resources/js/skel-layers.min.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/resources/js/init.js"/>'></script>
		
		<meta name="google-signin-client_id" content="627323629975-u2ufr9pe40kdkn50hl2r4q2b34bjnke2.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		
		<script type="text/javascript">

		window.onLoadCallback = function(){
      
		var auth2;
	    gapi.load('auth2', function(){
	        auth2= gapi.auth2.init({
			hosted_domain:'iiitd.ac.in'
			})
	    });
		  
		  gapi.signin2.render('my-signin2', {
	        'onsuccess': onSignIn
	      });
	  
    	}







		function onSignIn(googleUser) {
		  var profile = googleUser.getBasicProfile();
		  var id_token = googleUser.getAuthResponse().id_token;
		  console.log('ID Token: ' + id_token);
		  console.log('ID: ' + profile.getId());
		  console.log('Name: ' + profile.getName());
		  console.log('Image URL: ' + profile.getImageUrl());
		  console.log('Email: ' + profile.getEmail());
		  
		  window.location = '/Attendance/student/login?userID=' + profile.getId() + '&emailID=' + profile.getEmail() + '&name=' + profile.getName() + '&Image=' + profile.getImageUrl();
		}
		function onFailure(error) {
		    console.log(error);
		  }
		function renderButton() {
		  gapi.signin2.render('my-signin2', {
		    'scope': 'https://www.googleapis.com/auth/plus.login',
		    'width': 200,
		    'height': 50,
		    'longtitle': true,
		    'theme': 'dark',
		    'onsuccess': onSuccess,
		    'onfailure': onFailure
		  });
		
		}
		
		</script>
	</head>
	
	<body class="landing">
			<!-- Header -->
			<header id="header">
				
			</header>
			
			<!-- Banner -->
			
			<section id="banner">
				<h2>Welcome to Project Qatnash</h2>
				<ul class="actions">
					<li>

						<div  class="g-signin2" id="my-signin2" data-onsuccess="onSignIn" data-theme="dark" ></div>
			
					</li>
				</ul>
			</section>
	
	</body>
</html>