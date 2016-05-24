package com.zgardner.springBootIntro.controller;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.lang.Math.toIntExact;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zgardner.springBootIntro.Application;
import com.zgardner.springBootIntro.model.PersonModel;
import com.zgardner.springBootIntro.model.CreatePersonModel;
import com.zgardner.springBootIntro.repository.PersonRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PersonControllerITest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
    public void setup() {
		personRepository.deleteAll();
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void createPerson_success() throws Exception {
		String name = "Person name";
		CreatePersonModel createPersonModel = new CreatePersonModel();
		
		createPersonModel.setName(name);
		
		mockMvc.perform(post("/person/createPerson")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(createPersonModel)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name", is(name)));
	}
	
	@Test
	public void createPerson_failed() throws Exception {	
		mockMvc.perform(post("/person/createPerson"))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPersonById_success() throws Exception {
		String name = "Person name";
		PersonModel personModel = new PersonModel();
		
		personModel.setName(name);
		personRepository.save(personModel);
		
		Long id = personModel.getId();
		
		mockMvc.perform(get("/person/getPersonById/" + id))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(toIntExact(id))))
			.andExpect(jsonPath("$.name", is(name)));
	}
	
	@Test
	public void getPersonById_failed() throws Exception {
		Long id = (long) Math.random();

		mockMvc.perform(get("/person/getPersonById/" + id))
			.andDo(print())
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void getPersonByIdName_success() throws Exception {
		String name = "Person name";
		PersonModel personModel = new PersonModel();
		
		personModel.setName(name);
		personRepository.save(personModel);
		
		Long id = personModel.getId();
		
		mockMvc.perform(get("/person/getPersonById/" + id + "/name"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(name));
	}
	
	@Test
	public void getPersonByIdName_failed() throws Exception {
		Long id = (long) Math.random();
		
		mockMvc.perform(get("/person/getPersonById/" + id + "/name"))
			.andDo(print())
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void getPeopleByName_success() throws Exception {
		String name = "Person name";
		PersonModel personModel1 = new PersonModel();
		PersonModel personModel2 = new PersonModel();
		
		personModel1.setName(name);
		personModel2.setName(name);
		
		personRepository.save(personModel1);
		personRepository.save(personModel2);
		
		mockMvc.perform(get("/person/getPeopleByName/" + name))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(toIntExact(personModel1.getId()))))
			.andExpect(jsonPath("$[0].name", is(name)))
			.andExpect(jsonPath("$[1].id", is(toIntExact(personModel2.getId()))))
			.andExpect(jsonPath("$[1].name", is(name)));
	}
	
	@Test
	public void getPeopleByName_failed() throws Exception {
		String name = "Person name";
		
		mockMvc.perform(get("/person/getPeopleByName/" + name))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("[]"));
	}
	
	@Test
	public void getPeopleByNameSize_success() throws Exception {
		String name = "Person name";
		PersonModel personModel1 = new PersonModel();
		PersonModel personModel2 = new PersonModel();
		
		personModel1.setName(name);
		personModel2.setName(name);
		
		personRepository.save(personModel1);
		personRepository.save(personModel2);
		
		mockMvc.perform(get("/person/getPeopleByName/" + name + "/size"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("2"));
	}
	
	@Test
	public void getPeopleByNameSize_failed() throws Exception {
		String name = "Person name";
		
		mockMvc.perform(get("/person/getPeopleByName/" + name + "/size"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}
}
