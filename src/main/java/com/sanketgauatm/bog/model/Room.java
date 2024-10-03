package com.sanketgauatm.bog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name="rooms")
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "room_id")
    int id;
    String name;
    String location;
    int maxCapacity;
    @Enumerated(EnumType.STRING)
    Status status = Status.AVAILABLE;

    @OneToMany(mappedBy = "room")
    private Set<Conference> conferences;


    @OneToOne
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "user_id" )
    private User createdBy;

    public Room(){}

    public Room(String name, String location, int maxCapacity, User createdBy) {
        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.createdBy = createdBy;
    }

}
