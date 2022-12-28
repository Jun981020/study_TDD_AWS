package com.springboot.aws;

import com.springboot.aws.domain.user.Role;
import com.springboot.aws.domain.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing
public class AwsApplication {
	public static void main(String[] args) {
		SpringApplication.run(AwsApplication.class, args);
	}
}

