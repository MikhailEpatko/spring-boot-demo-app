package com.example.demo.api.v1;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BasicIT {

    public static PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>(
                DockerImageName.parse("postgres:15-alpine")
        )
                .withDatabaseName("farm_db")
                .withUsername("kate")
                .withPassword("password")
                .withReuse(true);
        postgres.start();
    }

    @DynamicPropertySource
    static void setPostgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
