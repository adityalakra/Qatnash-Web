package com.mongodb.controller;

import java.text.SimpleDateFormat;
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
	public View setStudentID(@RequestParam("userID") String userID, @RequestParam("emailID") String emailID, @RequestParam("name") String name, @RequestParam("Image") String Image, HttpSession session)
	{
		courses.createCoursesCollection();
		session.setAttribute("studentID",userID);
		session.setAttribute("emailID",emailID);
		session.setAttribute("name", name);
		session.setAttribute("Image", Image);
		System.out.println("Image" + Image);
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
			Students stud = students.getStudentDoc(new Query(Criteria.where("emailID").is(session.getAttribute("emailID"))));
			ArrayList<String> selectedCourses = stud.getCourse();
			System.out.println("Subjects: " + Subject1);
			StringTokenizer st2 = new StringTokenizer(Subject1, ",");
			 
			while (st2.hasMoreElements()) {
				//courseCode.add((String) st2.nextElement());
				//System.out.println(courseCode);
				Courses c1 = courses.getCourseDocument(new Query(Criteria.where("courseCode").is((String) st2.nextElement())));
				if((selectedCourses == null) || (!selectedCourses.contains(c1.getID())))
				{
					students.enterSemNSubject(null, new Query(Criteria.where("emailID").is(session.getAttribute("emailID"))), c1.getID());
					students.addAttendanceDoc(new Query(Criteria.where("emailID").is(session.getAttribute("emailID"))), c1.getCourseName());
				}
				
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
			model.addAttribute("Image", session.getAttribute("Image"));
			ArrayList<Attendance> attendance = s.getAttendance();
			if(attendance != null)
			{
				
				ArrayList<String> attendanceDates = new ArrayList<String>();
				SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY");
				for(Attendance a: attendance)
				{
					String d = null;
					if(a.getLastAttendanceDate()!= null)
					{
						d = format.format(a.getLastAttendanceDate());
					}
					
					attendanceDates.add(d);
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
		if(courseNames == null)
			return "redirect:updateProfile";
		System.out.println(courseNames);
		model.addAttribute("subjectsList", courseNames);
		Date d1 = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		Date d2 = c.getTime();
		dates[0] = d1; dates[1] = d2;
		SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY");
		model.addAttribute("d1", format.format(dates[0]));
		model.addAttribute("d2", format.format(dates[1]));
		if(session.getAttribute("message") != null)
		{
			model.addAttribute("message", session.getAttribute("message"));
			session.setAttribute("message", null);
		}
		
		return "markAttendance";
	}
	
	
	@RequestMapping(value="markAttendance/save", method=RequestMethod.POST)
	public View markAttendance(
			@RequestParam String subject, 
			@RequestParam String rowNo, 
			@RequestParam String seatNo,
			ModelMap model, RedirectAttributes redirect, HttpSession session)
	{
		Date date = null;
		int d = 1;
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
			
			//Courses c = courses.getCourseDocument(new Query(Criteria.where("courseName").is(courseNames.get(0).getCourseName())));
			//create the student collection only when lastAttendanceDate is true
			
				
			System.out.println("AttendanceCollection " + attendanceCollectionRepository.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode"))));
			AttendanceCollection a = new AttendanceCollection();
			if(attendanceCollectionRepository.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode").is(c1.getCourseCode())).addCriteria(Criteria.where("date").is(date))) == null)
			{
				AttendanceCollection a1 = new AttendanceCollection();
				a1.setCount(0);
				a1.setCourseCode(c1.getCourseCode());				
				a1.setDate(date);
				attendanceCollectionRepository.createAttendanceCollection(a1);
				courses.addDate(date, new Query(Criteria.where("courseCode").is(subject)));
				
			}
			AttendanceCollection attendanceCol = attendanceCollectionRepository.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode").is(c1.getCourseCode())).addCriteria(Criteria.where("date").is(date)));
			ArrayList<StudentAndSeat> s = attendanceCol.getStudents();
			if(s != null)
			{
				for(StudentAndSeat stud: s)
				{
					System.out.println(stud.getStudentID());
					String email = (String) session.getAttribute("emailID");
					if(stud.getStudentID().equalsIgnoreCase(email))
					{
						System.out.println("couldnt mark for " + session.getAttribute("emailID"));
						redirect.addFlashAttribute("message", "attendance unsuccessful");
						session.setAttribute("message", "attendance unsuccessful");
						return new RedirectView("/Attendance/markAttendance");
					}
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY");
			boolean success = students.increaseAttendance(c1, new Query(Criteria.where("emailID").is(session.getAttribute("emailID"))) , (dates[0]));
			attendanceCollectionRepository.addPresentStudent(students.getStudentDoc(new Query(Criteria.where("emailID").is(session.getAttribute("emailID")))).getEmailID(), new Query(Criteria.where("courseCode").is(subject)).addCriteria(Criteria.where("date").is(date)), rowNo, seatNo);
			System.out.println(success);
			session.setAttribute("message", "attendance successful");
		}
		return new RedirectView("/Attendance/markAttendance");
	}

	@RequestMapping(value="//sessionlogout")  
    public View logoutSession(HttpSession session) {
       
		session.invalidate();
		return new RedirectView("/Attendance");
		
    }
	
	
	
	//for app 

	@RequestMapping(value="/appLogin/{emailID}/{userID}/{name}", method=RequestMethod.GET)
	public String setStudentIDApp(@PathVariable("emailID") String emailID, @PathVariable("userID") String userID,
			@PathVariable("name") String name, ModelMap model, HttpSession session)
	{
		courses.createCoursesCollection();
		System.out.println("From App: " + userID + emailID + name);
		if(students.getStudentDoc(new Query(Criteria.where("emailID").is(emailID))) == null)
		{
			students.createStudentfromLogin(name, emailID, userID);
		}
		Students s = students.getStudentDoc(new Query(Criteria.where("emailID").is(emailID)));
		String sem = String.valueOf(s.getSemester());
		System.out.println(sem);
		model.addAttribute("semester", sem);
		return "profilePage";
	}
	
	@RequestMapping(value="//attendancePage/{emailID}", method=RequestMethod.GET)
	public String displayMarkAttendancePage(@PathVariable("emailID") String emailID, ModelMap model)
	{
		System.out.println(emailID);
		ArrayList<Courses> courseCodes = students.getSubjectIDs(new Query(Criteria.where("emailID").is(emailID)), courses);
		if(courseCodes == null)
			model.addAttribute("courseNames", null);
		System.out.println(courseCodes);
		ArrayList<String> courseNames = new ArrayList<String>();
		for(Courses c : courseCodes)
		{
			courseNames.add(c.getCourseName());
		}
		model.addAttribute("courseNames", courseNames);
		Date d1 = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		Date d2 = c.getTime();
		dates[0] = d1; dates[1] = d2;
		SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY");
		model.addAttribute("d1", format.format(dates[0]));
		model.addAttribute("d2", format.format(dates[1]));
		return "attendancePage_App";
	}
	
	@RequestMapping(value="//attendancePage/{emailID}/{courseName}/{rowNo}/{seatNo}/{dateIndex}", method=RequestMethod.GET)
	public String markAttendance(@PathVariable("emailID") String emailID, @PathVariable("courseName") String courseName, @PathVariable("rowNo") String rowNo, 
			@PathVariable("seatNo") String seatNo, @PathVariable("dateIndex") String dateIndex, ModelMap model)
			{
		Date date = null;
		System.out.println(Integer.parseInt(dateIndex));
		if((Integer.parseInt(dateIndex)) == 0)
		{
			date = dates[0];
		}
		else if ((Integer.parseInt(dateIndex)) == 1)
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
		Courses c1 = courses.getCourseDocument(new Query(Criteria.where("courseName").is(courseName)));
		if(courses.checkDate(date, new Query(Criteria.where("courseName").is(courseName))))
		{
			System.out.println(true);
			
			//Courses c = courses.getCourseDocument(new Query(Criteria.where("courseName").is(courseNames.get(0).getCourseName())));
			//create the student collection only when lastAttendanceDate is true
			
			AttendanceCollection a = new AttendanceCollection();
			if(attendanceCollectionRepository.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode").is(c1.getCourseCode())).addCriteria(Criteria.where("date").is(date))) == null)
			{
				AttendanceCollection a1 = new AttendanceCollection();
				a1.setCount(0);
				a1.setCourseCode(c1.getCourseCode());				
				a1.setDate(date);
				attendanceCollectionRepository.createAttendanceCollection(a1);
				courses.addDate(date, new Query(Criteria.where("courseCode").is(c1.getCourseCode())));
				
			}
			AttendanceCollection attendanceCol = attendanceCollectionRepository.getAttendanceDocumentQuery(new Query(Criteria.where("courseCode").is(c1.getCourseCode())).addCriteria(Criteria.where("date").is(date)));
			ArrayList<StudentAndSeat> s = attendanceCol.getStudents();
			if(s != null)
			{
				for(StudentAndSeat stud: s)
				{
					System.out.println(stud.getStudentID());
					if(stud.getStudentID().equalsIgnoreCase(emailID))
					{
						System.out.println("couldnt mark for " + emailID);
						model.addAttribute("isMarked", "false");
						return "marked_App";
					}
				}
			}
			
			boolean success = students.increaseAttendance(c1, new Query(Criteria.where("emailID").is(emailID)) , (dates[0]));
			attendanceCollectionRepository.addPresentStudent(students.getStudentDoc(new Query(Criteria.where("emailID").is(emailID))).getEmailID(), new Query(Criteria.where("courseCode").is(c1.getCourseCode())).addCriteria(Criteria.where("date").is(date)), rowNo, seatNo);
			System.out.println(success);
			model.addAttribute("isMarked", "true");
		}
				return "marked_App";
			}
	
	
	
	
	//@RequestMapping(value="//updateAttendance" , method=RequestMethod.GET)
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
	
	//@RequestMapping(value="updateAttendance/getCourse", method=RequestMethod.POST)
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


