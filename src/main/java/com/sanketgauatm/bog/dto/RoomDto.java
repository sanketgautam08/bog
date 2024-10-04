package com.sanketgauatm.bog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private int id;
    private String name;
    private String location;
    private int capacity;
    private int createdByUserId;

    public RoomDto(int id, String name, String location, int capacity, int createdByUserId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.createdByUserId = createdByUserId;

    }
}
