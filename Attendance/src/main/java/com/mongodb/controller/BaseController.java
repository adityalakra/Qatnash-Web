package com.mongodb.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.mongodb.domain.Person;
import com.mongodb.domain.PersonRepository;
import com.mongodb.entity.*;
@Controller

public class BaseController 
{
	@Autowired 
	private StudentsRepository students;
	
	@Autowired 
	private CoursesRepository courses;
	
	@Autowired
	private AttendanceCollectionRepository attendanceCollectionRepository;
	
	private String studentID;
	private Date[] dates = new Date[2];
	
	@RequestMapping(value = "/")
	public String getStudentLoginPage(ModelMap model, HttpSession session)
	{
		if(session.getAttribute(studentID)==null)
		{
			return "studentLogin";
		}
		else
		{
			return "Attendance/studentProfile";
		}
		
	}
	
	@RequestMapping(value="/student/login")
	public View setStudentID(@RequestParam("userID") String userID, @RequestParam("emailID") String emailID, @RequestParam("name") String name,HttpSession session)
	{
		session.setAttribute("studentID",userID);
		session.setAttribute("emailID",emailID);
		session.setAttribute("name", name);
		
		//this.studentID = userID;
		System.out.println("seesion attribute: " + session.getAttribute("studentID"));
		String email = emailID;
		System.out.println("email: " + email);
		if(students.getStudentDoc(new Query(Criteria.where("emailID").is(emailID))) == null)
		{
			students.createStudentfromLogin(name, emailID, userID);
		}
		
		return new RedirectView("/Attendance/studentProfile");
	}
	
	//for android app
	@RequestMapping(value="/appLogin/{userID}", method=RequestMethod.GET)
	public String setStudentIDApp(@PathVariable("userID") String userID, ModelMap model)
	{
		this.studentID = userID;
		System.out.println("From App: " + studentID);
		Students s = students.getStudentDoc(new Query(Criteria.where("userID").is(studentID)));
		model.addAttribute("name", s.getName());
		model.addAttribute("userID", s.getUserID());
		model.addAttribute("emailID", s.getEmailID());
		return "profilePage";
	}
	
	//redundant only for testing
	@RequestMapping(value = "/student/save", method = RequestMethod.POST)
	public View createStudentProfile(
					@RequestParam String name,
					@RequestParam String emailID, 
					@RequestParam String userID)
	{
		System.out.println("Name: " + name + "\nEmail: " + emailID + "\nUser ID" + userID);
		this.studentID = userID;
		students.createStudentfromLogin(name, emailID, userID);
		return new RedirectView("/Attendance"); 
	}
	
	@RequestMapping(value="//updateProfile", method=RequestMethod.GET)
	public String updateProfilePage(ModelMap model, HttpSession session)
	{
		if(session.getAttribute("studentID") == null)
		{
			return "studentLogin";
		}
		else
		{
			model.addAttribute("subjectsList", courses.getAllCourses());
			return "updateProfile";
		}
		
	}
	
	
	@RequestMapping(value = "/student/update", method = RequestMethod.POST)
	public View updateStudentProfile(
			@RequestParam(value="semester", required=false) String semester, HttpSession session,
			@RequestParam(value="Subject1", required=false) String Subject1)
			//@RequestParam String Subject2)
	{
		if(session.getAttribute("studentID") == null)
		{
			return new RedirectView("/Attendance"); 
		}
		
		Integer sem = null;
		if(!semester.isEmpty())
		{
			System.out.println("Semester: " + semester);
			sem = Integer.parseInt(semester);
			students.enterSemNSubject(sem, new Query(Criteria.where("emailID").is(session.getAttribute("emailID"))));
		}
		
		if(Subject1 != null)
		{
			System.out.println("Subjects: " + Subject1);
			StringTokenizer st2 = new StringTokenizer(Subject1, ",");
			 
			while (st2.hasMoreElements()) {
				//courseCode.add((String) st2.nextElement());
				//System.out.println(courseCode);
				Courses c1 = courses.getCourseDocument(new Query(Criteria.where("courseCode").is((String) st2.nextElement())));
				students.enterSemNSubject(null, new Query(Criteria.where("emailID").is(session.getAttribute("emailID"))), c1.getID());
				students.addAttendanceDoc(new Query(Criteria.where("emailID").is(session.getAttribute("emailID"))), c1.getCourseName());
			}
		}
		
		
		
//		Courses c1 = courses.getCourseDocument(new Query(Criteria.where("courseCode").is(Subject1)));
//		Courses c2 = courses.getCourseDocument(new Query(Criteria.where("courseCode").is(Subject2)));
//		
//		students.enterSemNSubject(sem, new Query(Criteria.where("userID").is(studentID)), c1.getID(), c2.getID());
//		students.addAttendanceDoc(new Query(Criteria.where("userID").is(studentID)), c1.getCourseName(), c2.getCourseName());
		return new RedirectView("/Attendance/studentProfile"); 
	}
	
	@RequestMapping(value="//studentProfile", method=RequestMethod.GET)
	public String viewProfile(ModelMap model, HttpSession session)
	{
		if(session.getAttribute("studentID") == null)
		{
			return "studentLogin"; 
		}
		else
		{
			String studentID = (String) session.getAttribute("emailID");
			Students s = students.getStudentDoc(new Query(Criteria.where("emailID").is(studentID)));
			model.addAttribute("studentName", s.getName());
		//	model.addAttribute("userID", s.getUserID());
			model.addAttribute("emailID", s.getEmailID());
			model.addAttribute("semester", s.getSemester());
			ArrayList<Attendance> attendance = s.getAttendance();
			if(attendance != null)
			{
				
				ArrayList<Date> attendanceDates = new ArrayList<Date>();
				for(Attendance a: attendance)
				{
					attendanceDates.add(a.getLastAttendanceDate());
				}
				model.addAttribute("attendanceList", attendance);
				model.addAttribute("lastAttendanceDates", attendanceDates);
			}
			
			return "studentProfile";
		}
		
	}
	
	@RequestMapping(value="//markAttendance", method=RequestMethod.GET)
	public String markAttendancePage(ModelMap model, HttpSession session)
	{
		if(session.getAttribute("studentID") == null)
		{
			return "studentLogin"; 
		}
		System.out.println("attendancePage: " + studentID);
		ArrayList<Courses> courseNames = students.getSubjectIDs(new Query(Criteria.where("emailID").is(session.getAttribute("emailID"))), courses);
		System.out.println(courseNames);
		model.addAttribute("subjectsList", courseNames);
		Date d1 = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		Date d2 = c.getTime();
		dates[0] = d1; dates[1] = d2;
		model.addAttribute("d1", dates[0]);
		model.addAttribute("d2", dates[1]);
		return "markAttendance";
	}
	
	
	@RequestMapping(value="markAttendance/save", method=RequestMethod.POST)
	public View markAttendance(
			@RequestParam String subject, 
			@RequestParam String rowNo, 
			@RequestParam String seatNo,
			@RequestParam int d, ModelMap model, RedirectAttributes redirect, HttpSession session)
	{
		Date date = null;
		if(d == 1)
		{
			date = dates[0];
		}
		else
		{
			date = dates[1];
		}
		Calendar presentDate = Calendar.getInstance();
		presentDate.setTime(date);
		presentDate.set(Calendar.HOUR_OF_DAY, 0);
		presentDate.set(Calendar.MINUTE, 0);
		presentDate.set(Calendar.SECOND, 0);
		presentDate.set(Calendar.MILLISECOND, 0);
		date = presentDate.getTime();
		Courses c1 = courses.getCourseDocument(new Query(Criteria.where("courseCode").is(subject)));
		if(courses.checkDate(date, new Query(Criteria.where("courseCode").is(subject))))
		{
			System.out.println(true);
			boolean success = students.increaseAttendance(c1, new Query(Criteria.where("emailID").is(session.getAttribute("emailID"))) , date);
			//Courses c = courses.getCourseDocument(new Query(Criteria.where("courseName").is(courseNames.get(0).getCourseName())));
			//create the student collection only when lastAttendanceDate is true
			System.out.println(success);
			
			if(success)
			{
				
				System.out.println("AttendanceCollection " + attendanceCollectionRepository.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode"))));
				AttendanceCollection a = new AttendanceCollection();
				if(attendanceCollectionRepository.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode").is(c1.getCourseCode())).addCriteria(Criteria.where("date").is(date))) == null)
				{
					AttendanceCollection a1 = new AttendanceCollection();
					a1.setCount(1);
					a1.setCourseCode(c1.getCourseCode());
					
					
					a1.setDate(date);
					attendanceCollectionRepository.createAttendanceCollection(a1);
					courses.addDate(date, new Query(Criteria.where("courseCode").is(subject)));
					model.addAttribute("message", "attendance added");
				}
				attendanceCollectionRepository.addPresentStudent(students.getStudentDoc(new Query(Criteria.where("emailID").is(session.getAttribute("emailID")))).getEmailID(), new Query(Criteria.where("courseCode").is(subject)).addCriteria(Criteria.where("date").is(date)), rowNo, seatNo);
			}
			redirect.addAttribute("message", "attendance unsuccessful");
			model.addAttribute("message", "attendance unsuccessful");
		}
		return new RedirectView("/Attendance/markAttendance");
	}

	@RequestMapping(value="//sessionlogout")  
    public View logoutSession(HttpSession session) {
       
		session.invalidate();
		return new RedirectView("/Attendance");
		
    }
	@RequestMapping(value="//updateAttendance" , method=RequestMethod.GET)
	public String viewUpdateAttendance(ModelMap model)
	{
		//Courses c = courses.getCourseDocument(new Query(Criteria.where("courseCode").is("CSE102")));
		ArrayList<Courses> subjects = courses.getAllCourses();
		model.addAttribute("subjectsList", courses.getAllCourses());
		 Date today = new Date();
			Calendar presentDate = Calendar.getInstance();
			presentDate.setTime(today);
			presentDate.set(Calendar.HOUR_OF_DAY, 0);
			presentDate.set(Calendar.MINUTE, 0);
			presentDate.set(Calendar.SECOND, 0);
			presentDate.set(Calendar.MILLISECOND, 0);
			today = presentDate.getTime();
			System.out.println("presentDATE: " + today);
			
			Calendar firstDate = Calendar.getInstance();
			firstDate.setTimeInMillis(0);
			firstDate.set(2015, 6, 1, 0, 0, 0);
			System.out.println("firstDate: " + firstDate.getTime());
			
			ArrayList<Date> allDates = new ArrayList<Date>();
			while(firstDate.getTime().before(today))
			{
				Date d = firstDate.getTime();
				allDates.add(d);
				firstDate.add(Calendar.DATE, 1);
			}
			
			//System.out.println(allDates);
		model.addAttribute("dateList", allDates);
		return "updateAttendance";
	}
	
	@RequestMapping(value="updateAttendance/getCourse", method=RequestMethod.POST)
	public String printDates(
			@RequestParam String courseCode, 
			@RequestParam int selectedDate,
			ModelMap model)
	{
		System.out.println(courseCode);
		System.out.println("Date" + selectedDate);
		return "updateAttendance";
	}
}
	
//	private static int counter = 0;
//	private static final String VIEW_INDEX = "output";
//	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);
//	
//	@Autowired
//	private PersonRepository personService;
//	
//    @RequestMapping(value = "/", method = RequestMethod.GET)  
//	public String getPersonList(ModelMap model) {  
//        model.addAttribute("personList", personService.listPerson());  
//        return "output";  
//    }  
//    
//    @RequestMapping(value = "/person/save", method = RequestMethod.POST)  
//	public View createPerson(@ModelAttribute Person person, ModelMap model) {
//    	if(StringUtils.hasText(person.getPersonId())) {
//    		personService.updatePerson(person);
//    	} else {
//    		personService.addPerson(person);
//    	}
//    	return new RedirectView("/Attendance");  
//    }
//        
//    @RequestMapping(value = "/person/delete", method = RequestMethod.GET)  
//	public View deletePerson(@ModelAttribute Person person, ModelMap model) {  
//        personService.deletePerson(person);  
//        return new RedirectView("/Attendance");  
//    } 
	
	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String welcome(ModelMap model) {
//		 
//		model.addAttribute("message", "Welcome");
//		model.addAttribute("counter", ++counter);
//		logger.debug("[welcome] counter : {}", counter);
// 
//		// Spring uses InternalResourceViewResolver and return back index.jsp
//		return VIEW_INDEX;
//	}
//	
//	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
//	public String welcomeName(@PathVariable String name, ModelMap model) {
// 
//		model.addAttribute("message", "Welcome " + name);
//		model.addAttribute("counter", ++counter);
//		logger.debug("[welcomeName] counter : {}", counter);
//		return VIEW_INDEX;
// 
//	}


