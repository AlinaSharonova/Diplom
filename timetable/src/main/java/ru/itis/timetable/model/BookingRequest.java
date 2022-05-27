package ru.itis.timetable.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_booking_request")
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_request_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "period")
    private BookingPeriod period;

    @Column(name = "time")
    @Enumerated(EnumType.STRING)
    private Time time;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToMany
    @JoinTable(name = "tb_booking_request_requirements",
            joinColumns = @JoinColumn(name = "booking_request_id"),
            inverseJoinColumns = @JoinColumn(name = "requirement_id"))
    private Set<Requirement> requirements;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "email")
    private String email;

    @Column(name = "c_group")
    private String group;

}
