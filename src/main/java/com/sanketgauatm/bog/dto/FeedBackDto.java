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
}
