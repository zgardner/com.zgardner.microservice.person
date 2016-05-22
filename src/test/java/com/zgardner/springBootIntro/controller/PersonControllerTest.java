package com.zgardner.springBootIntro.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.lang.Math.toIntExact;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.zgardner.springBootIntro.Application;
import com.zgardner.springBootIntro.service.PersonService;
import com.zgardner.springBootIntro.model.PersonModel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PersonControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
    private DefaultListableBeanFactory beanFactory;
	
	@Mock
	private PersonService personService;
	
	@InjectMocks
	private PersonController personController;
	
	@Before
    public void setup() {
		initMocks(this);
		
		beanFactory.destroySingleton("personController");
		beanFactory.registerSingleton("personController", personController);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void getPersonById() throws Exception {
		Long id = 999L;
		String name = "Person name";
		
		when(personService.findById(id)).thenReturn(new PersonModel(id, name));
		
		mockMvc.perform(get("/person/getPersonById/" + id))
			.andDo(print())
			.andExpect(jsonPath("$.id", is(toIntExact(id))))
			.andExpect(jsonPath("$.name", is(name)));
	}
}
