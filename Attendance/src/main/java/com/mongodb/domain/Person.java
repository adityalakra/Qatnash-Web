package com.mongodb.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Person {

    @Id
    private String id;

    private String firstName;
    private String name; 
    private String homeTown; 
    private int age; 
    public Person(String name, int age) 
    { 
    	this.name = name; 
    	this.age = age; 
    }
    public Person()
    {
    	
    }
    public String getPersonId() 
    {
    	return id; 
    }
    public void setPersonId(final String personId) 
    { 
    	this.id = personId; 
    } 
    public String getName() 
    { 
    	return name; 
    }
    public void setName(final String name) 
    {
    	this.name = name; 
    }
    public int getAge() 
    { 
    	return age; 
    }
    public void setAge(final int age)
    { 
    	this.age = age; 
    }
     
    public String getHomeTown() 
    { 
    	return homeTown; 
    } 
    public void setHomeTown(final String homeTown)
    { 
    	this.homeTown = homeTown;
    } 
    @Override public String toString() 
    { return "Person [id=" + id + ", name=" + name + ", "
    		+ "age=" + age + ", home town=" + homeTown + "]";
    }  
    
}