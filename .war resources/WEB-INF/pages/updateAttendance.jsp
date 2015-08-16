<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
		
		<title> Update Attendance </title>
		<script type="text/javascript" src='<c:url value="/resources/js/jquery-1.11.3.min.js"/>'></script>
		<script type="text/javascript" src='<c:url value="/resources/js/functions.js"/>'></script>
		<script type="text/javascript" src='<c:url value="/resources/js/jquery.tablesorter.js"/>'></script>
		
		<style type = "text/css">
			select {
				font-size: 20px;
				font-family: 'Trebuchet MS';
			}	​
			table { 
				font-size: 20px;
				font-family: 'Trebuchet MS';
				border: 1px solid black;
				border-collapse:collapse;
			}	
			th, td{ 
				border: 1px solid black;
				padding: 5px;
			}
			#mydropdown1{
				width:180px;   
			}
			.tablesorter{
				display: none;
			}
		</style>
	</head>
	<body style = 'font-family: "Trebuchet MS"'>
	
		<center><h1> Update Attendance </h1></center>
		<hr/>
		<form name="attendance" action="updateAttendance/getCourse" method="POST">
		<h2> Course </h2>
			<c:forEach var="course" items="${subjectsList}">
			<input type="radio" name="courseCode" value=${course.courseCode}>${course.courseName}
			<br>
			</c:forEach>
			
			<select name="selectedDate">

				<c:forEach var="dates" items="${dateList}" varStatus="loop">
				<option value=${loop.index}>${dates}</option>
				</c:forEach>
			</select>	
			<br>

			
			<!-- <option value="June, 25th 2015">June, 25th 2015</option>
			<option value="June, 24th 2015">June, 24th 2015</option>
			<option value="June, 23th 2015">June, 23th 2015</option> -->
			<button type="submit">Submit</button>
		</form>	
		
		<br>
		<script type = "text/javascript">
			var flag = 1;
			function showHide(){
				if(flag == 1){
					document.getElementById("tblData").style.display="block";
					flag = 0;
				}
				else{
					document.getElementById("tblData").style.display="none";
					flag = 1;
				}
			}
			
			var prof = 1;
			document.addEventListener('DOMContentLoaded', function() {
				if(prof == 1){
				document.getElementById("button1").style.display="block";
			}
			}, false);
			
			function deleteRow(r) {
				var i = r.parentNode.parentNode.rowIndex;
				document.getElementById("tblData").deleteRow(i);
			}
			
			
		</script>
		
		<br>
		<input type="button" value = "Update" id = "button1" class = "tablesorter" body style = 'font-family: "Trebuchet MS"; font-size: 20px' onclick = "showHide()" ></button>
		<br>
		<br>
		
		<table id = "tblData" class = "tablesorter">
			<thead>
			<tr>
				<th>Name of the Student</th>
				<th>Roll Number</th>
				<th>Seat Number</th>
			
			</tr>
			</thead>
			<tbody>
			<tr>
			
				<td><div contenteditable>Abc</div></td>
				<td><div contenteditable>2011<div></td>
				<td><div contenteditable>4<div></td>
				<td>
					<input type="button" onclick="deleteRow(this)" value = "Delete" body style = 'font-family: "Trebuchet MS"; font-size: 20px'></button>
				</td>	
			</tr>
			<tr>
				<td><div contenteditable>Aaa<div></td>
				<td><div contenteditable>2013<div></td>
				<td><div contenteditable>3<div></td>
				<td>
					<input type="button" onclick="deleteRow(this)" value = "Delete" body style = 'font-family: "Trebuchet MS"; font-size: 20px'></button>
				</td>	
			</tr>
			<tr>
				<td><div contenteditable>Pqr<div></td>
				<td><div contenteditable>2012<div></td>
				<td><div contenteditable>2<div></td>
				<td>
					<input type="button" onclick="deleteRow(this)" value = "Delete" body style = 'font-family: "Trebuchet MS"; font-size: 20px'></button>
				</td>	
			</tr>
			<tr>
				<td><div contenteditable>Ijk<div></td>
				<td><div contenteditable>2014<div></td>
				<td><div contenteditable>1<div></td>
				<td>
					<input type="button" onclick="deleteRow(this)" value = "Delete" body style = 'font-family: "Trebuchet MS"; font-size: 20px'></button>
				</td>	
			</tr>
			</tbody>
		</table>
	</body>
</html>