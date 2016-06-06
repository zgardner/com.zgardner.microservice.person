package com.zgardner.microservice.person.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zgardner.microservice.person.model.CreatePersonModel;
import com.zgardner.microservice.person.model.PersonModel;
import com.zgardner.microservice.person.repository.PersonRepository;

@Service
public class PersonService {
	
	private PersonRepository personRepository;
	
	@Autowired
	public PersonService (PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	public PersonModel createPerson(CreatePersonModel createPersonModel) {
		PersonModel personModel = new PersonModel();
		personModel.setName(createPersonModel.getName());
		
		personRepository.save(personModel);
		
		return personModel;
	}
	
	public PersonModel findById(Long id) {
		return personRepository.findById(id);
	}
	
	public List<PersonModel> findByName(String name) {
		return personRepository.findByName(name);
	}
}
