package io.github.javaclub.javersdemo.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import io.github.javaclub.javersdemo.data.EmailsConverter;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @Convert(converter= EmailsConverter.class)
    private List<Email> emails;

    @Transient
    private Map<PhoneType, Phone> phones;

    @ManyToOne
    private Manager manager;

}
