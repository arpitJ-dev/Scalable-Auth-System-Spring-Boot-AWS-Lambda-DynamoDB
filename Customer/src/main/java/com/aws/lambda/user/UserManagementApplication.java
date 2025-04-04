package com.aws.lambda.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Spring Boot application class for User Management System.
 * This application provides a serverless user management API using AWS Lambda.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.aws.lambda.user")
public class UserManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementApplication.class, args);
    }

}
