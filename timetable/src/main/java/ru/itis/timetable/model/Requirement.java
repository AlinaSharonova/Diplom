package ru.itis.timetable.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_requirement")
public class Requirement {

    @Id
    @Column(name = "requirement_id")
    private UUID id;

    @Column(name = "type")
    private String type;
}
