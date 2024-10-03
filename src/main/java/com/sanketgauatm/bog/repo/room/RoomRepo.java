package com.sanketgauatm.bog.repo.room;

import com.sanketgauatm.bog.model.Room;
import org.springframework.data.repository.ListCrudRepository;

public interface RoomRepo extends ListCrudRepository<Room, Integer>, CustomRoomRepo {
}
