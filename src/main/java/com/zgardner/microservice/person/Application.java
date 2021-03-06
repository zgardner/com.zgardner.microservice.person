package com.zgardner.microservice.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableSwagger2
public class Application 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(Application.class, args);
    	
    	System.out.println(System.getProperty("project.build.directory"));
    }
    
    @Bean
    public Docket api () {
    	return new Docket(DocumentationType.SWAGGER_2)
    				.apiInfo(apiInfo())
    				.select()
    				.build();
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
    				.title("Person API")
    				.description("Spring Boot intro doing Person API")
    				.contact("Zach Gardner")
    				.version("1.0")
    				.build();
    }
}
