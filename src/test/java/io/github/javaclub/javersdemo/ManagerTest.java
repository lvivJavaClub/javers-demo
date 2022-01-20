package io.github.javaclub.javersdemo;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import io.github.javaclub.javersdemo.model.Customer;
import io.github.javaclub.javersdemo.model.Email;
import io.github.javaclub.javersdemo.model.EmailType;
import io.github.javaclub.javersdemo.model.Manager;
import io.github.javaclub.javersdemo.model.Phone;
import io.github.javaclub.javersdemo.model.PhoneType;
import org.javers.core.JaversBuilder;
import org.junit.jupiter.api.Test;

class ManagerTest {

    @Test
    void compareManager() {

        var manager1 = Manager.builder()
            .id(100)
            .firstName("John")
            .lastName("Doe")
            .bonus(100)
            .build();
        manager1.setUpdatedAt(Instant.now());

        var manager1_2 = manager1.toBuilder()
            .lastName("King")
            .build();
        manager1_2.setUpdatedAt(Instant.now().plusSeconds(100));

        var javers = JaversBuilder.javers().build();

        var diff = javers.compare(manager1, manager1_2);
        System.out.println("diff.prettyPrint() = " + diff.prettyPrint());

        diff.getChanges().forEach(System.out::println);


        var customer1 = Customer.builder()
            .id(200)
            .firstName("Bob")
            .lastName("Snail")
            .birthDate(LocalDate.of(1980, 01, 01))
            .emails(List.of(
                Email.builder().emailType(EmailType.PERSONAL).emailAddress("a@b.c").build(),
                Email.builder().emailType(EmailType.WORK).emailAddress("a2@b.c").build()
            ))
            .phones(Map.of(
                    PhoneType.PERSONAL, new Phone(PhoneType.PERSONAL, "123-11111"),
                    PhoneType.URGENT, new Phone(PhoneType.URGENT, "123-0000")
                )
            )
            .build();

        var customer1_2 = customer1.toBuilder()
            .birthDate(LocalDate.of(1980, 01, 04))
            .emails(List.of(
                Email.builder().emailType(EmailType.PERSONAL).emailAddress("a@gmail.com").build(),
                Email.builder().emailType(EmailType.WORK).emailAddress("a2@b.c").build(),
                Email.builder().emailType(EmailType.OTHER).emailAddress("a32@b.c").build()
            ))
            .phones(Map.of(
                    PhoneType.PERSONAL, new Phone(PhoneType.PERSONAL, "123-33333"),
                    PhoneType.URGENT, new Phone(PhoneType.URGENT, "123-0000"),
                    PhoneType.WORK, new Phone(PhoneType.URGENT, "123-7777")
                )
            )
            .build();

        System.out.println("javers.compare(customer1, customer1_2).prettyPrint() = " +
            javers.compare(customer1, customer1_2).prettyPrint());


        manager1.setCustomers(List.of(customer1));
        manager1_2.setCustomers(List.of(customer1_2));

        System.out.println("javers.compare(manager1, manager1_2).prettyPrint() = " +
            javers.compare(manager1, manager1_2).prettyPrint());

    }

}
