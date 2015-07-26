package com.mongodb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.mongodb.domain.Person;
import com.mongodb.domain.PersonRepository;

public class PersonController 
{
	@Autowired
	private PersonRepository personService;
	
    @RequestMapping(value = "/", method = RequestMethod.GET)  
	public String getPersonList(ModelMap model) {  
        model.addAttribute("personList", personService.listPerson());  
        return "output";  
    }  
    
    @RequestMapping(value = "/person/save", method = RequestMethod.POST)  
	public View createPerson(@ModelAttribute Person person, ModelMap model) {
    	if(StringUtils.hasText(person.getPersonId())) {
    		personService.updatePerson(person);
    	} else {
    		personService.addPerson(person);
    	}
    	return new RedirectView("/HelloSpringWithMongoDB/person");  
    }
        
    @RequestMapping(value = "/person/delete", method = RequestMethod.GET)  
	public View deletePerson(@ModelAttribute Person person, ModelMap model) {  
        personService.deletePerson(person);  
        return new RedirectView("/HelloSpringWithMongoDB/person");  
    } 

}
