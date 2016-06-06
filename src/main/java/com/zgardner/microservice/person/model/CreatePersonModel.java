package com.zgardner.microservice.person.model;

public class CreatePersonModel {	
	private String name;
	
	public CreatePersonModel() {
		
	}
	
	public CreatePersonModel(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}