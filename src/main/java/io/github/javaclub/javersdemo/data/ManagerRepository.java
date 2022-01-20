package io.github.javaclub.javersdemo.data;

import io.github.javaclub.javersdemo.model.Manager;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
