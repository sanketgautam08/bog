package com.sanketgauatm.bog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Integer id;
    private String firstName;
    private String LastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany(mappedBy = "users")
    private Set<Conference> conferences = new HashSet<>();

    @OneToOne(mappedBy = "createdBy")
    private Room room;

    @OneToOne(mappedBy = "feedbackUser")
    private Feedback feedback;

    private LocalDate dob;

    public User() {
    }

    public void setGender(String gender){
        this.gender = Gender.valueOf(gender);
    }
    public User(String firstName, String lastName, Gender gender, LocalDate dob) {
        this.firstName = firstName;
        this.LastName = lastName;
        this.gender = gender;
        this.dob = dob;
    }
    public User(int id, String firstName, String lastName, Gender gender, LocalDate dob) {
        this.id = id;
        this.firstName = firstName;
        this.LastName = lastName;
        this.gender = gender;
        this.dob = dob;
    }
}

