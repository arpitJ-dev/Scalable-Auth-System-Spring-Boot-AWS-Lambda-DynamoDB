package com.aws.lambda.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Integration tests for User Management Application.
 * Tests the Spring Boot application context loading and basic functionality.
 */
@SpringBootTest
@ActiveProfiles("test")
class UserManagementApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
    }

}
