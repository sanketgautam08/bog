package com.sanketgauatm.bog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import java.util.Set;

@Entity
@Table(name="conference")
@Getter
@Setter
public class Conference {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "conference_id")
    private Integer id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
        name = "conference_users",
        joinColumns = @JoinColumn(name = "conference_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date_time")
    private LocalDate dateTime;

    @OneToOne(mappedBy = "feedbackConference")
    private Feedback feedback;

    @OneToOne(mappedBy = "conference_id", cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Confirmation confirmation;

    public Conference(){}

    public Conference(String name, String description, Room room, LocalDate dateTime) {
        this.name = name;
        this.description = description;
        this.room = room;
        this.dateTime = dateTime;
    }

    public Conference(int id){
        this.id = id;
    }


}
