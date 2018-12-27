package org.wisestar.oracle2java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackageClasses = {Oracle2javaApplication.class})
@EnableCaching
@EnableScheduling
public class Oracle2javaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oracle2javaApplication.class, args);
	}
}
