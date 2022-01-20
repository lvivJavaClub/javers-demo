package io.github.javaclub.javersdemo.model;

import java.time.Instant;

import lombok.Data;
import org.javers.core.metamodel.annotation.DiffIgnore;

@Data
public class Auditable {

    @DiffIgnore
    private Instant createdAt;
    @DiffIgnore
    private String createdBy;
    @DiffIgnore
    private Instant updatedAt;
    @DiffIgnore
    private String updatedBy;

}
