package com.mongodb.domain;

import java.util.List; 

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.mongodb.core.MongoTemplate; 
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository; 
/** * Repository for {@link Person}s */ 

@Repository 
public class PersonRepository 
{ 
	static final Logger logger = LoggerFactory.getLogger(PersonRepository.class); 
	
	@Autowired 
	MongoTemplate mongoTemplate; 
	
	public void logAllPersons() 
	{ List<Person> results = mongoTemplate.findAll(Person.class); 
		logger.info("Total amount of persons: {}", results.size()); 
		logger.info("Results: {}", results); 
	}
	public void insertPersonWithNameJohnAndRandomAge() 
	{ //get random age between 1 and 100 
		double age = Math.ceil(Math.random() * 100); 
		Person p = new Person("John", (int) age); 
		mongoTemplate.insert(p); 
		System.out.println("Inserted " + p);
		
	} 
	/** * Create a {@link Person} collection if the collection does not already exists */ 
	public void createPersonCollection() 
	{ 
		if (!mongoTemplate.collectionExists("Person.class")) 
			{ 
				mongoTemplate.createCollection("Person.class"); 
			} 
	} 
	
	/** * Drops the {@link Person} collection if the collection does already exists */ 
	public void dropPersonCollection() 
	{ 
		if (mongoTemplate.collectionExists("Person.class")) 
		{ 
			mongoTemplate.dropCollection("Person.class"); 
		}
	} 
	
	public void updatePerson(Query query, Update update)
	{
		mongoTemplate.updateMulti(query, update, Person.class);
		System.out.println("Updated!");
	}
	
	public Person executeQuery(Query query)
	{
		Person p = mongoTemplate.findOne(query, Person.class);
//		System.out.println(p.getPersonId());
		return p;
		
	}
	
	public List<Person> listPerson()
	{
		return mongoTemplate.findAll(Person.class, "person");
	}
	
	public void deletePerson(Person person) {
		mongoTemplate.remove(person, "person");
	}
	
	public void updatePerson(Person person) {
		mongoTemplate.insert(person, "person");		
	}
	
	public void addPerson(Person person)
	{
		mongoTemplate.insert(person, "person");
	}
}