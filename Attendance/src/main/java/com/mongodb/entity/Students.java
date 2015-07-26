package com.mongodb.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Students
{
	@Id
	private String id;
	
	private String userID;
	private String name;
	private String emailID;
	private Integer semester;
	private ArrayList<Attendance> attendance;
	//we can use manual referencing.
	//however, this can be replaced with simple embedded documents of courses selected.
	//to reduce overload; 
	private ArrayList<String> courses;
	
	public String getUserID()
	{
		return userID;
	}
	public void setUserID(String id)
	{
		this.userID = id;
	}
	public String getStudentID()
	{
		return id;
	}	
	public void setStudentID(String StudentID)
	{
		this.id = StudentID;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public void setEmailID(String emailID)
	{
		this.emailID = emailID;
	}	
	public String getEmailID()
	{
		return emailID;
	}
	public void setSemester(Integer Semester)
	{
		this.semester = Semester;
	}	
	public Integer getSemester()
	{
		return semester;
	}
	public void addCourse(String id)
	{
		this.courses.add(id);
	}
	public ArrayList<String> getCourse()
	{
		return courses;
	}
	public void addCoursetoAttendance(Attendance a) //add attendance one by one
	{
		this.attendance.add(a);
	}
	public ArrayList<Attendance> getAttendance() //get complete array
	{
		return attendance;
	}
		
	
}
