package com.zgardner.springBootIntro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zgardner.springBootIntro.model.PersonModel;
import com.zgardner.springBootIntro.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	private PersonService personService;
	
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@RequestMapping(value = "/getPersonById/{id}", method = RequestMethod.GET)
	public PersonModel getPersonById (@PathVariable Long id) {
		PersonModel personModel = personService.findById(id);
		
		if (personModel == null) {
			throw new ResourceNotFoundException();
		}
		
		return personModel;
	}
	
	@RequestMapping(value = "/getPersonById/{id}/name", method = RequestMethod.GET)
	public String getPersonNameById (@PathVariable Long id) {
		PersonModel personModel = personService.findById(id);
		
		if (personModel == null) {
			throw new ResourceNotFoundException();
		}
		
		return personModel.getName();
	}
	
	@RequestMapping(value = "/getPeopleByName/{name:.+}", method = RequestMethod.GET)
	public List<PersonModel> getPeopleByName (@PathVariable String name) {
		List<PersonModel> personModels = personService.findByName(name);
		
		return personModels;
	}
	
	@RequestMapping(value = "/getPeopleByName/{name:.+}/size", method = RequestMethod.GET)
	public int getPeopleByNameSize (@PathVariable String name) {
		List<PersonModel> personModels = personService.findByName(name);
		
		return personModels.size();
	}
}
