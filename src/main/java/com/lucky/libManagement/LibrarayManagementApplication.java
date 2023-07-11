package com.lucky.libManagement;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.boot.web.servlet.MultipartConfigFactory;

@EnableWebMvc
@SpringBootApplication //It encapsulates @SpringBootConfiguration, @EnableAutoConfiguration, and @ComponentScan 
@ComponentScan(basePackages = "com.lucky.libManagement")
public class LibrarayManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarayManagementApplication.class, args);
	}
	
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(10)); // Set the maximum file size
        factory.setMaxRequestSize(DataSize.ofMegabytes(10)); // Set the maximum request size
        return factory.createMultipartConfig();
    }


}
