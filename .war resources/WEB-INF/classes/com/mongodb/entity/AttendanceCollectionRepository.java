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
/** * Repository for {@link AttendanceCollection}s */ 
@Repository
public class AttendanceCollectionRepository 
{
	@Autowired 
	MongoTemplate mongoTemplate; 
	
	//create collection
	public void createAttendancceCollection()
	{
		System.out.println(mongoTemplate.collectionExists(AttendanceCollection.class));
		if (!mongoTemplate.collectionExists(AttendanceCollection.class)) 
		{ 
			mongoTemplate.createCollection(AttendanceCollection.class); 
		} 
		System.out.println(mongoTemplate.collectionExists(AttendanceCollection.class));
	}
	
	//delete collection if it exists
	public void dropAttendanceCollection() 
	{ 
		System.out.println(mongoTemplate.collectionExists(AttendanceCollection.class));
		if (mongoTemplate.collectionExists(AttendanceCollection.class)) 
		{ 
			mongoTemplate.dropCollection(AttendanceCollection.class); 
		}
		System.out.println(mongoTemplate.collectionExists(AttendanceCollection.class));
	} 
	
	
	
	//get single Attendance document using date and courseCode
	public AttendanceCollection getAttendanceDocumentQuery(Query query)
	{
		AttendanceCollection c = mongoTemplate.findOne(query, AttendanceCollection.class);
		System.out.println(c);
		return c;		
	}
	
	//public AttendanceCollection getAttendanceDoc 
	
	//get multiple Attendance documents of a single Course
	public ArrayList<AttendanceCollection> getMultipleAttendanceDocument(Query q)
	{
		return (ArrayList<AttendanceCollection>) mongoTemplate.find(q, AttendanceCollection.class);
	}
	
	
	//update the desired document extracted using Query and update it
	public void updateCourse(Query query, Update update)
	{
		mongoTemplate.updateFirst(query, update, Courses.class);
		System.out.println("Updated Courses!");
	}
	
	
	
	//save AttendanceCollection Document with date, courseCode 
	//method called when document for the course and date does not exist ie when the first child marks attendance
	//when findOne return null
	public void createAttendanceCollection(AttendanceCollection a)
	{
		mongoTemplate.insert(a);
	}
	
	//Query q to find the required Collection
	public void updateAttendanceCollection(Query q, Update u)
	{
		mongoTemplate.updateFirst(q, u, AttendanceCollection.class);
		System.out.println("updated attendance collection!");
	}
	
	//call this method only if incAttendance method of StudentRepository return true.
	//if false, that means, lastAttendanceDate is clashing
	//Query q to find the attendance Doc
	//rowNo in small letters
	public void addPresentStudent(String studentID, Query q, String rowNo, String seatNo)
	{
		StudentAndSeat s= new StudentAndSeat();
		s.setRowNo(rowNo);
		s.setSeatNo(seatNo);
		s.setStudentID(studentID);
		Update u = new Update().push("presentStudents", s);
		updateAttendanceCollection(q, u);
		Integer count = getAttendanceDocumentQuery(q).getCount();
		count = count + 1;
		Update u1 = new Update().set("count", count);
		updateAttendanceCollection(q, u1);
		
	}
	
	//delete sub Doc of Proxy Students
	//pass the attendance Doc
	//pass query for update functipn
	public boolean deleteProxy(AttendanceCollection a, String studentID, StudentsRepository students, Query q, String courseName)
	{
		ArrayList<StudentAndSeat> presentStudents = a.getStudents();
		for(StudentAndSeat s: presentStudents)
		{
			if(s.getStudentID().equals(studentID))
			{
				Update u = new Update().pull("presentStudents", s);
				updateAttendanceCollection(q, u);
				
				reduceAttendanceStudent(students, studentID, courseName);
				return true;
			}
		}
		return false;
	}
	
	//update student Collection
	private void reduceAttendanceStudent(StudentsRepository students, String studentID, String courseName)
	{
		Students s = students.getStudentDoc(new Query(Criteria.where("userID").is(studentID)));
		ArrayList<Attendance> attendance = s.getAttendance();
		
		for(Attendance a : attendance)
		{
			if(a.getCourseName().equalsIgnoreCase(courseName))
			{
				int attendance1 = a.getTotalAttendance();
				attendance1 = attendance1 - 1;
				students.updateStudentProfile((new Query(Criteria.where("userID").is(studentID)).addCriteria(Criteria.where("attendance.courseName").is(courseName))), new Update().set("attendance.$.totalAttendance", attendance));
				System.out.println("Present Attendance: CourseName- " + courseName + " Attendance- " + a.getTotalAttendance());
			}
		}
	}

}
