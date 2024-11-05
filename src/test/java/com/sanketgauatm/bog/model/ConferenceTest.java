package com.sanketgauatm.bog.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConferenceTest {

    @Test
    void shouldCreateNewConference() {
        var conference = new Conference("con1", "first conference", new Room(1), LocalDateTime.now(), LocalDateTime.now());

        assertEquals("con1", conference.getName(), "Conference name should be 'con1'");
        assertEquals("first conference", conference.getDescription(), "Conference description should be 'first conference'");
        assertInstanceOf(Room.class, conference.getRoom(), "Room should be instance of Room");
    }

    @Test
    void shouldUpdateConference() {
        var conference = new Conference("con1", "first conference", new Room(1), LocalDateTime.now(), LocalDateTime.now());
        conference.setDescription("new description");

        assertThat(conference.getDescription())
                .isEqualTo("new description");
    }

}