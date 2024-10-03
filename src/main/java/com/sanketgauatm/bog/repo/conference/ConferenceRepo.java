package com.sanketgauatm.bog.repo.conference;

import com.sanketgauatm.bog.model.Conference;
import com.sanketgauatm.bog.model.Room;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDate;

public interface ConferenceRepo extends ListCrudRepository<Conference, Integer>, CustomRepo  {
    Conference findByRoomAndDateTime(Room room, LocalDate dateTime);
}
