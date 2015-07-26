package com.mongodb.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Attendance 
{
	@Id
	private String id;
	private String courseName;
	private Integer totalAttendance = 0;
	//match last marked date of attendance with current one. Current should be after lastAttendanceDate
	//date stored as day/month/year
	private Date lastAttendanceDate = null; 
									
	public void setID(String objectId)
	{
		this.id = objectId;
	}
	public String getID()
	{
		return id;
	}
	public void setCourseName(String name)
	{
		this.courseName = name;
	}
	public String getCourseName()
	{
		return courseName;
	}
	public void setTotalAttendance(Integer num)
	{
		this.totalAttendance = num;
	}
	public Integer getTotalAttendance()
	{
		return totalAttendance;
	}
	public void setDate(Date date)
	{
		this.lastAttendanceDate = date;
	}
	public Date getLastAttendanceDate()
	{
		return lastAttendanceDate;
	}
}
