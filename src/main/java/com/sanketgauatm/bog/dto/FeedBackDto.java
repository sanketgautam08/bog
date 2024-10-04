package com.sanketgauatm.bog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedBackDto {
    private int conferenceId;
    private String feedbackText;
    private int rating;
    private String userName;

    public FeedBackDto(int conferenceId, String feedbackText, int rating, String userName) {
        this.conferenceId = conferenceId;
        this.feedbackText = feedbackText;
        this.rating = rating;
        this.userName = userName;
    }
}
