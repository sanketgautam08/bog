package com.sanketgauatm.bog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Confirmation {
    @Id
    private String confirmationNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "conference_id", referencedColumnName = "conference_id")
    private Conference conference_id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;

    public Confirmation() {}

    public Confirmation(String confirmationNumber, Conference conference_id, User user_id) {
        this.confirmationNumber = confirmationNumber;
        this.conference_id = conference_id;
        this.userId = user_id;
    }
}
