package com.zgardner.microservice.person.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.Math.toIntExact;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.zgardner.microservice.person.Application;
import com.zgardner.microservice.person.controller.PersonController;
import com.zgardner.microservice.person.model.PersonModel;
import com.zgardner.microservice.person.service.PersonService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PersonControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private PersonService personService;
	
	@InjectMocks
	private PersonController personController;
	
	@Before
    public void setup() {
		initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
	}
	
	@Test
	public void getPersonById_success() throws Exception {
		Long id = (long) Math.random();
		String name = "Person name";
		
		when(personService.findById(id)).thenReturn(new PersonModel(id, name));
		
		mockMvc.perform(get("/person/getPersonById/" + id))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(toIntExact(id))))
			.andExpect(jsonPath("$.name", is(name)));
	}
	
	@Test
	public void getPersonById_failure() throws Exception {
		Long id = (long) Math.random();
		
		when(personService.findById(anyLong())).thenReturn(null);
		
		mockMvc.perform(get("/person/getPersonById/" + id))
			.andDo(print())
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void getPersonNameById_success() throws Exception {
		Long id = (long) Math.random();
		String name = "Person name";
		
		when(personService.findById(id)).thenReturn(new PersonModel(id, name));
		
		mockMvc.perform(get("/person/getPersonById/" + id + "/name"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(name));
	}
	
	@Test
	public void getPersonNameById_failure() throws Exception {
		Long id = (long) Math.random();
		
		when(personService.findById(anyLong())).thenReturn(null);
		
		mockMvc.perform(get("/person/getPersonById/" + id + "/name"))
			.andDo(print())
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void getPeopleByName_success() throws Exception {
		Long id1 = (long) Math.random();
		Long id2 = (long) Math.random();
		String name = "People name";
		List<PersonModel> people = new ArrayList<PersonModel>();
		
		people.add(new PersonModel(id1, name));
		people.add(new PersonModel(id2, name));
		
		when(personService.findByName(name)).thenReturn(people);
		
		mockMvc.perform(get("/person/getPeopleByName/" + name))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(toIntExact(id1))))
			.andExpect(jsonPath("$[0].name", is(name)))
			.andExpect(jsonPath("$[1].id", is(toIntExact(id2))))
			.andExpect(jsonPath("$[1].name", is(name)));
	}
	
	@Test
	public void getPeopleByName_failure() throws Exception {
		String name = "People name";
		List<PersonModel> people = new ArrayList<PersonModel>();
		
		when(personService.findByName(anyString())).thenReturn(people);
		
		mockMvc.perform(get("/person/getPeopleByName/" + name))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("[]"));
	}
	
	@Test
	public void getPeopleByNameSize_success() throws Exception {
		Long id1 = (long) Math.random();
		Long id2 = (long) Math.random();
		String name = "People name";
		List<PersonModel> people = new ArrayList<PersonModel>();
		
		people.add(new PersonModel(id1, name));
		people.add(new PersonModel(id2, name));
		
		when(personService.findByName(name)).thenReturn(people);
		
		mockMvc.perform(get("/person/getPeopleByName/" + name + "/size"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("2"));
	}
	
	@Test
	public void getPeopleByNameSize_failure() throws Exception {
		String name = "People name";
		List<PersonModel> people = new ArrayList<PersonModel>();
		
		when(personService.findByName(anyString())).thenReturn(people);
		
		mockMvc.perform(get("/person/getPeopleByName/" + name + "/size"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("0"));
	}
}
