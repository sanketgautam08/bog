package com.sanketgauatm.bog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableConference {
    private int conferenceId;
    private String name;
    private int availableSeats;

    public AvailableConference(int conferenceId, String name, int availableSeats) {
        this.conferenceId = conferenceId;
        this.name = name;
        this.availableSeats = availableSeats;
    }
}
