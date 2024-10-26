package com.sanketgauatm.bog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;

import java.util.Set;

@Entity
@Table(name="conference")
@Getter
@Setter
public class Conference {

    @Id
    @SequenceGenerator(name="conference_seq", sequenceName="conference_seq", allocationSize=1)
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

//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @OneToOne(mappedBy = "feedbackConference")
    private Feedback feedback;

    @OneToOne(mappedBy = "conference_id", cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Confirmation confirmation;

    public Conference(){}

    public Conference(String name, String description, Room room, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.name = name;
        this.description = description;
        this.room = room;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Conference(int id){
        this.id = id;
    }


}
