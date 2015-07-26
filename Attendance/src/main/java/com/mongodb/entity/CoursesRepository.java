package com.mongodb.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.domain.Person;
import com.mongodb.domain.PersonRepository;
/** * Repository for {@link Courses}s */ 
@Repository
public class CoursesRepository 
{
	@Autowired 
	MongoTemplate mongoTemplate; 
	
	//create collection
	public void createCoursesCollection()
	{
		System.out.println(mongoTemplate.collectionExists(Courses.class));
		if (!mongoTemplate.collectionExists(Courses.class)) 
		{ 
			mongoTemplate.createCollection(Courses.class); 
		} 
		System.out.println(mongoTemplate.collectionExists(Courses.class));
	}
	
	//delete collection if it exists
	public void dropCoursesCollection() 
	{ 
		System.out.println(mongoTemplate.collectionExists(Courses.class));
		if (mongoTemplate.collectionExists(Courses.class)) 
		{ 
			mongoTemplate.dropCollection(Courses.class); 
		}
		System.out.println(mongoTemplate.collectionExists(Courses.class));
	} 
	
	//redundant: only for test case
	public void createCoursesDocuments()
	{
		Courses c = new Courses();
		c.setCourseName("OS");
		Random r = new Random();
		String n = String.valueOf(r.nextInt());
		c.setCourseCode(n);
		mongoTemplate.insert(c);
		System.out.println("Inserted Courses");
	}
	
	
	//get selected courses document based on desired factor
	public Courses getCourseDocument(Query query)
	{
		Courses c = mongoTemplate.findOne(query, Courses.class);
		System.out.println(c);
		return c;		
	}
	
	public ArrayList<Courses> getAllCourses()
	{
		return (ArrayList<Courses>) mongoTemplate.findAll(Courses.class);
	}
	
	
	//update the desired document extracted using Query and update it
	public void updateCourse(Query query, Update update)
	{
		mongoTemplate.updateFirst(query, update, Courses.class);
		System.out.println("Updated Courses!");
	}
	
	//redundant: for test case
	public ArrayList<String> getPersonID()
	{
		return new Courses().getPersonID();
	}
	
	
	//save courses document. Course c prepared in Controller by taking parameters.
	public void createCourse(Courses c)
	{
		mongoTemplate.insert(c);
	}
	
	//check validity of date with class
	//pass query to get the course document
	public boolean checkDate(Date date, Query q)
	{
		Courses c = getCourseDocument(q);
		ArrayList<Integer> days = c.getDay();
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		//sunday is 0;		
		Integer dayOfDate = calender.get(Calendar.DAY_OF_WEEK);
		for(Integer d: days)
		{
			System.out.println("Check Date" + dayOfDate + " " + d);
			if(dayOfDate == d)
			{
				
				return true;
			}
				
		}
		return false;
	}
	
	//add Dates on which attendance was marked
	//to be shown as drop down menu to TA
	public void addDate(Date d, Query q)
	{
		Update u = new Update().push("attendanceDates", d);
		updateCourse(q, u);
	}
	
	public ArrayList<Date> getClassDates(Query q)
	{
		Courses c = getCourseDocument(q);
		ArrayList<Date> dates = c.getClassDates();
		Date d1 = new Date();
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.DATE, -1);
		Date d2 = calender.getTime();
		Calendar presentDate = Calendar.getInstance();
		presentDate.setTime(d1);
		presentDate.set(Calendar.HOUR_OF_DAY, 0);
		presentDate.set(Calendar.MINUTE, 0);
		presentDate.set(Calendar.SECOND, 0);
		presentDate.set(Calendar.MILLISECOND, 0);
		d1 = presentDate.getTime();
		
		presentDate.setTime(d2);
		presentDate.set(Calendar.HOUR_OF_DAY, 0);
		presentDate.set(Calendar.MINUTE, 0);
		presentDate.set(Calendar.SECOND, 0);
		presentDate.set(Calendar.MILLISECOND, 0);
		d2 = presentDate.getTime();
		System.out.println(dates);
		for(int i = 0; i < dates.size(); i++)
		{
			presentDate.setTime(dates.get(i));
			presentDate.set(Calendar.HOUR_OF_DAY, 0);
			presentDate.set(Calendar.MINUTE, 0);
			presentDate.set(Calendar.SECOND, 0);
			presentDate.set(Calendar.MILLISECOND, 0);
			dates.set(i,  presentDate.getTime());
			//remove when final
//			if(((dates.get(i).equals(d1)) || (dates.get(i).equals(d2))))
//			{
//				dates.remove(i);
//			}
		}
		System.out.println(dates);
		return dates;
	}
}
