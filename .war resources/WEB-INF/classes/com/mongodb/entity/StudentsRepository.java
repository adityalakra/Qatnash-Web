package com.mongodb.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.domain.Person;
import com.mongodb.domain.PersonRepository;
/** * Repository for {@link Students}s */ 
@Repository
public class StudentsRepository 
{
	@Autowired 
	MongoTemplate mongoTemplate;
	
	    //create collection
		public void createStudentsCollection()
		{
			System.out.println(mongoTemplate.collectionExists(Students.class));
			if (!mongoTemplate.collectionExists(Students.class)) 
			{ 
				mongoTemplate.createCollection(Students.class); 
			} 
			System.out.println(mongoTemplate.collectionExists(Students.class));
		}
		
		//delete collection if it exists
		public void dropStudentsCollection() 
		{ 
			System.out.println(mongoTemplate.collectionExists(Students.class));
			if (mongoTemplate.collectionExists(Students.class)) 
			{ 
				mongoTemplate.dropCollection(Students.class); 
			}
			System.out.println(mongoTemplate.collectionExists(Students.class));
		}
		
		//get particular Student document
		//based on desired query. Most probably using userID or emailID
		public Students getStudentDoc(Query q)
		{
			Students student = mongoTemplate.findOne(q, Students.class);
			System.out.println(student);
			return student;
		}
		
		//create Student Profile. With general info with UserID, emailID, Name
		public void createStudent(Students s)
		{
			mongoTemplate.insert(s);
		}
		
		//call from controller directly, just pass on the arguments got from html pages
		//creates student from the login id
		public void createStudentfromLogin(String Name, String emailID, String UserID)
		{
			Students s = new Students();
			s.setEmailID(emailID);
			s.setName(Name);
			s.setUserID(UserID);
			createStudent(s);
		}
		
		//for update options
		public void updateStudentProfile(Query q, Update u)
		{
			mongoTemplate.updateFirst(q, u, Students.class);
			System.out.println("updated student profile!");
		}
		
		//add subjects to the arrays and update course
		//pass null value for fields in the controller that are not updated
		//send direct query to find students document using appropriate field
		//attendance sub doc initialized with just courseName
		public void enterSemNSubject(Integer semester, Query q,String...subjectIDs)
		{
			if(semester != null)
			{
				Update u = new Update().set("semester", semester);
				updateStudentProfile(q, u);
			}
			System.out.println(subjectIDs);
			for(String id : subjectIDs)
			{
				Update u = new Update().push("courses", id);
				updateStudentProfile(q, u);								
			}
		}
		
		public void addAttendanceDoc(Query q,String...courseNames)
		{
			for(String names: courseNames)
			{
				Attendance a = new Attendance();
				a.setCourseName(names);
				updateStudentProfile(q, new Update().push("attendance", a));
			}
		}
		
		//increase attendance value for the course in the student document
		//Query q to find the particular student document
		//Take Date argument of the date for which attendance is being marked
		//return false on some error
		public boolean increaseAttendance(Courses c, Query q, Date date)
		{
			//Courses c = new CoursesRepository().getCourseDocument(new Query(Criteria.where("_id").is(_id)));
			Students s = getStudentDoc(q);
			String Name = c.getCourseName();
						
			ArrayList<Attendance> attendanceDocs = new ArrayList<Attendance>();
			attendanceDocs = s.getAttendance();
			for(Attendance a : attendanceDocs)
			{
				if(a.getCourseName().equalsIgnoreCase(Name))
				{
					//update attendance only when the marked attendance is for a date after LastAttendanceDate
					Calendar lastDate = Calendar.getInstance();
					Calendar presentDate = Calendar.getInstance();
					if (a.getLastAttendanceDate() != null)
					{
						lastDate.setTime(a.getLastAttendanceDate());
						lastDate.set(Calendar.HOUR_OF_DAY, 0);
						lastDate.set(Calendar.MINUTE, 0);
						lastDate.set(Calendar.SECOND, 0);
						lastDate.set(Calendar.MILLISECOND, 0);
						
						presentDate.setTime(date);
						presentDate.set(Calendar.HOUR_OF_DAY, 0);
						presentDate.set(Calendar.MINUTE, 0);
						presentDate.set(Calendar.SECOND, 0);
						presentDate.set(Calendar.MILLISECOND, 0);
					}
					
					
					{
						updateStudentProfile(q.addCriteria(Criteria.where("attendance.courseName").is(Name)), new Update().set("attendance.$.lastAttendanceDate", date));
						int attendance = a.getTotalAttendance();
						attendance++;
						updateStudentProfile(q, new Update().set("attendance.$.totalAttendance", attendance));
						return true;
					}
					
				}
			}
			return false;
			
		}
		
		//pass query from controller to get the student doc
		//returns the subject documents itself
		//can change according to need
		//pass the courseRepsitory instance from the controller class
		public ArrayList<Courses> getSubjectIDs(Query q, CoursesRepository courses)
		{
			Students s = getStudentDoc(q);
			ArrayList<String> courseIDs = s.getCourse();
			ArrayList<Courses> coursesList = new ArrayList<Courses>();
			if(courseIDs == null)
				return null;
			for(String id : courseIDs)
			{
				Courses c = courses.getCourseDocument(new Query(Criteria.where("_id").is(id)));
				coursesList.add(c);
			}
			return coursesList;
		}
}
	
