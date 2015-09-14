package com.mongodb.entity;
import com.mongodb.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Courses 
{
	@Id
	private String id;
	
	private String courseName;
	private String courseCode;
	private String instructor;
	private ArrayList<Integer> yearArr = new ArrayList<Integer>();
	private ArrayList<Integer> classDays = new ArrayList<Integer>();
	private ArrayList<Date> attendanceDates = new ArrayList<Date>(); //contains the dates of classes;
	
	
	private ArrayList<Person> persons = new ArrayList<Person>(); //not using
	
	private ArrayList<String> personID;
	
	public void setID(String id)
	{
		this.id = id;
	}	
	public String getID()
	{
		return id;
	}
	public void setCourseName(String CourseName)
	{
		this.courseName = CourseName;
	}
	public String getCourseName()
	{
		return courseName;
	}
	public void setCourseCode(String code)
	{
		this.courseCode = code;
	}
	public String getCourseCode()
	{
		return courseCode;
	}
	public void setInstructor(String instructor)
	{
		this.instructor = instructor;
	}
	public String getInstructor()
	{
		return instructor;
	}
	public void addDay(int Date)
	{
		this.classDays.add(Date);
	}
	public ArrayList<Integer> getDay()
	{
		return classDays;
	}
	
	public ArrayList<Date> getClassDates()
	{
		return attendanceDates;
	}
	//add Date when classes took place;
	public void addDate(Date d)
	{
		attendanceDates.add(d);
	}
	
	
	public void setPerson(Person person)
	{
		persons.add(person);
		
	}
	public void setPersonID(String id)
	{
		this.personID.add(id);
	}
	public ArrayList<String> getPersonID()
	{
		return personID;
	}
	public ArrayList<Person> getPerson()
	{
		return persons;
	}
	

	
	
}
