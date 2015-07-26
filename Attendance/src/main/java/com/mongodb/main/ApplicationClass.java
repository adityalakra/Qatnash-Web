package com.mongodb.main;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.mongodb.DBObject;
import com.mongodb.domain.*;
import com.mongodb.entity.Courses;
import com.mongodb.entity.CoursesRepository;

import org.bson.types.ObjectId;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext; 
import org.springframework.context.support.ClassPathXmlApplicationContext; 
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


/** * Small MongoDB application that uses spring data to interact with MongoDB. */ 
public class ApplicationClass
{ 
	static final Logger logger = LoggerFactory.getLogger(ApplicationClass.class); 
	
	public static void main( String[] args ) 
	{ 
		logger.info("Bootstrapping MongoDemo application"); 
		//@SuppressWarnings("resource")
		PersonRepository personRepository;
		CoursesRepository courseRepository;
		//This thing works only when we are in a jar file, need classPath reference
		//http://stackoverflow.com/questions/4802877/right-path-to-applicationcontext-xml-using-classpathxmlapplicationcontext
		
		/*try(ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml"))
		{
			personRepository = context.getBean(PersonRepository.class);
		}*/
		
		ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml");
		personRepository = context.getBean(PersonRepository.class);
		courseRepository = context.getBean(CoursesRepository.class);
		
//		courseRepository.dropCoursesCollection();
//		courseRepository.createCoursesCollection();
//		for(int i = 0; i< 20; i++)
//		{
//			courseRepository.createCoursesDocuments();
//		}
////		 cleanup person collection before insertion 
//		personRepository.dropPersonCollection(); //create person collection<br /> 
//		personRepository.createPersonCollection(); 
//		System.out.println("Dropped Collection");
//		
//		for(int i=0; i<20; i++) 
//		{ 
//			personRepository.insertPersonWithNameJohnAndRandomAge(); 
//		} 
		
		personRepository.logAllPersons(); 
		logger.info("Finished MongoDemo application"); 
		
		Query query = new Query(Criteria.where("age").is(92));
		
//		
		Person p = personRepository.executeQuery(query);
		
		Query q = new Query((Criteria.where("courseCode").is("25217484")));
//		Update u = Update.update("persons", p);
		Courses c = courseRepository.getCourseDocument(q);
		System.out.println(c);
		Update u = new Update().push("persons", p);
		Person p3 = new Person();
		p3.setName("Jon");
		p3.setAge(62);
		p3.setPersonId(UUID.randomUUID().toString());
		p3.setHomeTown("Delhi");
		//courseRepository.updateCourse(q, new Update().push("persons", p3));
		
//		courseRepository.addPerson(c, p); //not adding to the arrayList
		//courseRepository.updateCourse(q, u);
		ArrayList<Person> person = c.getPerson();
		
		//update sub document
		//Query return the index of the array
		//courseRepository.updateCourse(q.addCriteria(Criteria.where("persons.age").is(92)), new Update().set("persons.$.name", "Johny"));
		Person p2 = person.get(0);
		System.out.println(p2);
//		ArrayList<String> l = c.getPersonID();
//		System.out.println(l);
//		Query q2 = new Query((Criteria.where("_id").is(l.get(2)))); //this is working in getting the required collection;
//		System.out.println(personRepository.executeQuery(q2));
		
		
		/*//to check date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	    Date date = null;
		try {
			date = dateFormat.parse("2015/06/28");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dateFormat.format(date));
		System.out.println(courses.checkDate(date, new Query(Criteria.where("courseCode").is("CSE101"))));*/
	} 
}

	
