package com.mongodb.main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.entity.*;



public class TestClass 
{
	public static void main( String[] args )
	{
		StudentsRepository students;
		CoursesRepository courses;
		AttendanceCollectionRepository attendanceCol;
		ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml");
		students = context.getBean(StudentsRepository.class);
		courses = context.getBean(CoursesRepository.class);
		attendanceCol = context.getBean(AttendanceCollectionRepository.class);
		
		
		
		//create and update Course Table
//		courses.createCoursesCollection();
//		Courses c1 = new Courses();
//		c1.addDay(7);
//		c1.addDay(1);
//		c1.addDay(2);
//		c1.addDay(4);
//		c1.addDay(5);
//		c1.addDay(6);
//		c1.addDay(3);
//		c1.setCourseCode("CSE101");
//		c1.setCourseName("Operating Systems");
//		c1.setInstructor("Pushpendra");
//		courses.createCourse(c1);
//		
//		Courses c2 = new Courses();
//		c2.addDay(7);
//		c2.addDay(1);
//		c2.addDay(2);
//		c2.addDay(3);
//		c2.addDay(5);
//		c2.addDay(6);
//		c2.addDay(4);
//		c2.setCourseCode("CSE102");
//		c2.setCourseName("ADA");
//		c2.setInstructor("Rajiv Raman");
//		courses.createCourse(c2);
//		
//		Courses c3 = new Courses();
//		c3.addDay(7);
//		c3.addDay(1);
//		c3.addDay(2);
//		c3.addDay(4);
//		c3.addDay(5);
//		c3.addDay(6);
//		c3.addDay(3);
//		c3.setCourseCode("CSE103");
//		c3.setCourseName("PS");
//		c3.setInstructor("Rajiv Raman");
//		courses.createCourse(c3);
		
		//to create students table
//		students.createStudentsCollection();
//		students.createStudentfromLogin("Protichi", "protichi13075@iiitd.ac.in", "2013075");
//		students.createStudentfromLogin("Amod", "amod13128@iiitd.ac.in", "2013128");
//		students.createStudentfromLogin("Nikita", "nikita13068@iiitd.ac.in", "2013068");
//		students.createStudentfromLogin("Chanchal", "chanchal13030@iiitd.ac.in", "2013030");
//		
//		//update
//		students.enterSemNSubject(5, new Query(Criteria.where("userID").is("2013030")));
//		students.addAttendanceDoc(new Query(Criteria.where("userID").is("2013030")));
//		Courses course1 = courses.getCourseDocument(new Query(Criteria.where("courseCode").is("CSE101")));
//		Courses courseB = courses.getCourseDocument(new Query(Criteria.where("courseCode").is("CSE102")));
//		students.enterSemNSubject(null, new Query(Criteria.where("userID").is("2013075")), course1.getID(), courseB.getID());
//		students.addAttendanceDoc(new Query(Criteria.where("userID").is("2013075")), course1.getCourseName(), courseB.getCourseName());
//		students.enterSemNSubject(6, new Query(Criteria.where("userID").is("2013128")), course1.getID());
//		students.addAttendanceDoc( new Query(Criteria.where("userID").is("2013128")), course1.getCourseName());
////		
		//for attendance increasing with date
//	    Courses c3 = courses.getCourseDocument(new Query(Criteria.where("courseCode").is("CSE102")));
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//	    Date date = null;
//		try {
//			date = dateFormat.parse("2015/06/30");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 Date d = new Date();
//		System.out.println(students.increaseAttendance(c3, new Query(Criteria.where("userID").is("2013075")), d));
		
		
		/*
		 * Date comparisons are not working anywhere
		 * neither is attendanceUpdate date working
		 * nor attedanceCollection date is working
		 * 
		 * */
		//create Attendance Collections
		//attendanceCol.createAttendancceCollection();
		//mark attendance
//		Date d = new Date();
//		System.out.println(d);
//		ArrayList<Courses> courseNames = students.getSubjectIDs(new Query(Criteria.where("userID").is("2013075")), courses);
//		System.out.println(courseNames);
//		Calendar presentDate = Calendar.getInstance();
//		presentDate.setTime(d);
//		presentDate.set(Calendar.HOUR_OF_DAY, 0);
//		presentDate.set(Calendar.MINUTE, 0);
//		presentDate.set(Calendar.SECOND, 0);
//		presentDate.set(Calendar.MILLISECOND, 0);
//		d = presentDate.getTime();
//		System.out.println(d);
//		if(courses.checkDate(d, new Query(Criteria.where("courseName").is(courseNames.get(0).getCourseName()))))
//		{
//			System.out.println(true);
//			boolean success = students.increaseAttendance(courseNames.get(0), new Query(Criteria.where("userID").is("2013075")) , d);
//			//Courses c = courses.getCourseDocument(new Query(Criteria.where("courseName").is(courseNames.get(0).getCourseName())));
//			Courses c = courseNames.get(0);
//			//create the student collection only when lastAttendanceDate is true
//			System.out.println(success);
//			
//			if(success)
//			{
//				
//				System.out.println("AttendanceCollection " + attendanceCol.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode"))));
//				AttendanceCollection a = new AttendanceCollection();
//				if(attendanceCol.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode").is(c.getCourseCode())).addCriteria(Criteria.where("date").is(d))) == null)
//				{
//					AttendanceCollection a1 = new AttendanceCollection();
//					a1.setCount(1);
//					a1.setCourseCode(c.getCourseCode());
//					
//					
//					a1.setDate(d);
//					attendanceCol.createAttendanceCollection(a1);
//				}
//				
//				String seatNo = "B123";
//				
//				attendanceCol.addPresentStudent(students.getStudentDoc(new Query(Criteria.where("userID").is("2013075"))).getStudentID(), new Query(Criteria.where("courseCode").is(c.getCourseCode())).addCriteria(Criteria.where("date").is(d)), seatNo);
//			}
//			
//		}
//		
		//to test date
//		Date d = new Date();
//		Calendar presentDate = Calendar.getInstance();
//		presentDate.setTime(d);
//		presentDate.set(Calendar.HOUR_OF_DAY, 0);
//		presentDate.set(Calendar.MINUTE, 0);
//		presentDate.set(Calendar.SECOND, 0);
//		presentDate.set(Calendar.MILLISECOND, 0);
//		d = presentDate.getTime();
//		
//		Date d1 = new Date();
//		presentDate.setTime(d1);
//		presentDate.set(Calendar.HOUR_OF_DAY, 0);
//		presentDate.set(Calendar.MINUTE, 0);
//		presentDate.set(Calendar.SECOND, 0);
//		presentDate.set(Calendar.MILLISECOND, 0);
//		d1 = presentDate.getTime();
//		
//		System.out.println("Date1 :" + d + "Date 2:" + d1 + " " + (d.equals(d1)));
//		
		//removing Attendance
//		 Date today = new Date();
//		 Calendar presentDate = Calendar.getInstance();
//		presentDate.setTime(today);
//		presentDate.set(Calendar.HOUR_OF_DAY, 0);
//		presentDate.set(Calendar.MINUTE, 0);
//		presentDate.set(Calendar.SECOND, 0);
//		presentDate.set(Calendar.MILLISECOND, 0);
//		today = presentDate.getTime();
//		
//		String courseCode = "CSE102";
//		Query q = new Query(Criteria.where("courseCode").is(courseCode)).addCriteria(Criteria.where("date").is(today));
//		AttendanceCollection a = attendanceCol.getAttendanceDocumentQuery(q);
//		ArrayList<StudentAndSeat> presentStud = a.getStudents();
//		System.out.println("Present Students: ");
//		for(StudentAndSeat s : presentStud)
//		{
//			System.out.println(s.getStudentID() + s.getSeatNo() + s.getRowNo());
//		}
//		
//		System.out.println("Deleting students with same seat");
//		
//		Courses c = courses.getCourseDocument(new Query(Criteria.where("courseCode").is(courseCode)));
//		System.out.println(attendanceCol.deleteProxy(a, "2013128", students, q, c.getCourseName()));

//		 Date today = new Date();
//		Calendar presentDate = Calendar.getInstance();
//		presentDate.setTime(today);
//		presentDate.set(Calendar.HOUR_OF_DAY, 0);
//		presentDate.set(Calendar.MINUTE, 0);
//		presentDate.set(Calendar.SECOND, 0);
//		presentDate.set(Calendar.MILLISECOND, 0);
//		today = presentDate.getTime();
//		System.out.println("presentDATE: " + today);
//		
//		Calendar firstDate = Calendar.getInstance();
//		firstDate.setTimeInMillis(0);
//		firstDate.set(2015, 6, 1, 0, 0, 0);
//		
//		System.out.println("firstDate: " + firstDate.getTime());
//		
//		ArrayList<Date> allDates = new ArrayList<Date>();
//		while(firstDate.getTime().before(today))
//		{
//			Date d = firstDate.getTime();
//			allDates.add(d);
//			firstDate.add(Calendar.DATE, 1);
//		}
//		
//		System.out.println(allDates);
		
		//mark attendance
//		Calendar firstDate = Calendar.getInstance();
//		firstDate.setTimeInMillis(0);
//		firstDate.set(2015, 6, 22, 0, 0, 0);
//		Date d = firstDate.getTime();
//		
//		System.out.println("firstDate: " + firstDate.getTime());
//		ArrayList<AttendanceCollection> col = attendanceCol.getMultipleAttendanceDocument(new Query(Criteria.where("courseCode").is("CSE102")));
//		System.out.println(col.get(1).getDate());
//		AttendanceCollection a = attendanceCol.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode").is("CSE102")).addCriteria(Criteria.where("date").is(d)));
//		ArrayList<StudentAndSeat> s = a.getStudents();
//		for(StudentAndSeat stud: s)
//		{
//			if(stud.getStudentID().equalsIgnoreCase("basaktanusree73@gmail.com"))
//			{
//				System.out.println("present");
//			}
//		}
		
	}


}
