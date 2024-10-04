package com.sanketgauatm.bog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConferenceDto {
    private int id;
    private String name;
    private String description;
    private int roomId;
    private LocalDate date;

    public ConferenceDto(int id, String name, String description, int roomId, LocalDate date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roomId = roomId;
        this.date = date;
    }
}
