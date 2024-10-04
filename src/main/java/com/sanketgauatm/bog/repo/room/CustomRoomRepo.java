package com.sanketgauatm.bog.repo.room;

import com.sanketgauatm.bog.dto.RoomDto;
import com.sanketgauatm.bog.model.Room;

import java.util.List;

public interface CustomRoomRepo {
    Integer getRoomCapacity(Integer roomId);
    List<RoomDto> getAllRooms();
    boolean validateCapacityAndStatus(Room room);
}
