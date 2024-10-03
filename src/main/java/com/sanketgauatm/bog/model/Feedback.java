package com.sanketgauatm.bog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer feedbackId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User feedbackUser;

    @OneToOne
    @JoinColumn(name = "conference_id", referencedColumnName = "conference_id")
    private Conference feedbackConference;

    private String feedbackText;
    private int rating;
    private String userName;

    public Feedback() {}

    public Feedback(User feedbackUser, Conference feedbackConference, String feedbackText, int rating) {
        this.feedbackUser = feedbackUser;
        this.feedbackConference = feedbackConference;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }

}
