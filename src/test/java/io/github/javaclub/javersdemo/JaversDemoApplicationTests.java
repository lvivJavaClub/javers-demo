package io.github.javaclub.javersdemo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import io.github.javaclub.javersdemo.data.ManagerRepository;
import io.github.javaclub.javersdemo.model.Customer;
import io.github.javaclub.javersdemo.model.Email;
import io.github.javaclub.javersdemo.model.EmailType;
import io.github.javaclub.javersdemo.model.Manager;
import io.github.javaclub.javersdemo.model.Phone;
import io.github.javaclub.javersdemo.model.PhoneType;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Sql(scripts = "classpath:schemas.sql")
class JaversDemoApplicationTests {

    @Container
    private static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer();

    @DynamicPropertySource
    public static void withPostgres(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", () -> postgresqlContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgresqlContainer.getPassword());
        registry.add("spring.datasource.url", () -> postgresqlContainer.getJdbcUrl());
    }

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private Javers javers;

    @Test
    void contextLoads() {
        var manager = Manager.builder()
            .firstName("John")
            .lastName("Doe")
            .bonus(122)
            .build();

        var customer = Customer.builder()
            .firstName("Customer")
            .lastName("1")
            .birthDate(LocalDate.of(1980, 01, 01))
            .emails(List.of(
                new Email().setEmailType(EmailType.PERSONAL).setEmailAddress("a@b.c")
            ))
            .manager(manager)
            .build();

        manager.setCustomers(List.of(customer));

        managerRepository.save(manager);

        var changes = javers.findChanges(QueryBuilder.byInstance(manager).build());

        customer.getEmails().get(0).setEmailAddress("changed.email@gmail.com");
        managerRepository.save(manager);

        var snapshots = javers.findSnapshots(QueryBuilder.byInstanceId(customer.getId(), Customer.class).build());

        var shadows = javers.<Customer>findShadows(QueryBuilder.byInstanceId(customer.getId(), Customer.class).build());

        for (Shadow<Customer> shadow : shadows) {
            var cust = shadow.get();
            System.out.println("cust = " + cust);
        }

        System.out.println("the end");

    }

}
