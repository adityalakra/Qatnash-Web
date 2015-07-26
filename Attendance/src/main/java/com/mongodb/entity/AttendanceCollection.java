package com.mongodb.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import sun.util.calendar.BaseCalendar.Date;

@Document
public class AttendanceCollection 
{
	@Id
	private String id;
	//just enter the days of classes. Then match the selected date with the day of attendance. 
	//Sunday is 0
	//getDay returns int
	private java.util.Date date;
	private String courseCode;
	private Integer count;
	private ArrayList<StudentAndSeat> presentStudents;
	private int verified = 1; //need to create the method.
	
	public void setID(String id)
	{
		this.id = id;
	}
	public String getID()
	{
		return id;
	}
	public void setCourseCode(String courseCode)
	{
		this.courseCode = courseCode;
	}
	public String getCourseCode()
	{
		return courseCode;
	}
	public void setDate(java.util.Date d)
	{
		this.date = d;
	}
	public java.util.Date getDate()
	{
		return date;
	}
	public void setCount(Integer count)
	{
		this.count = count;
	}
	public Integer getCount()
	{
		return count;
	}
	public void addStudents(StudentAndSeat s)
	{
		this.presentStudents.add(s);
	}
	public ArrayList<StudentAndSeat> getStudents()
	{
		return presentStudents;
	}
	public void setVerified(int i)
	{
		this.verified = i;
	}
	public int getVerified()
	{
		return verified;
	}
	
}
