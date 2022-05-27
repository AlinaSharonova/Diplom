package ru.itis.timetable.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_auditory")
public class Auditory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditory_id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "address")
    private String address;

    @ManyToMany
    @JoinTable(name = "tb_auditory_requirements",
            joinColumns = @JoinColumn(name = "auditory_id"),
            inverseJoinColumns = @JoinColumn(name = "requirement_id"))
    private Set<Requirement> requirements;

    private Integer capacity;

}
