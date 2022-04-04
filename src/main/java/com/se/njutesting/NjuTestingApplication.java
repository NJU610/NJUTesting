package com.se.njutesting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.se.njutesting.module.dao")
public class NjuTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NjuTestingApplication.class, args);
	}

}
