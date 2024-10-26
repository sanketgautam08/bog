package com.sanketgauatm.bog.dto;

import com.sanketgauatm.bog.model.Conference;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConferenceDto {
    private int id;
    private String name;
    private String description;
    private int roomId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public ConferenceDto(int id, String name, String description, int roomId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roomId = roomId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public ConferenceDto(Conference conference) {
        this.id = conference.getId();
        this.name = conference.getName();
        this.description = conference.getDescription();
        this.roomId = conference.getRoom().getId();
        this.startDateTime = conference.getStartDateTime();
        this.endDateTime = conference.getEndDateTime();
    }
}
