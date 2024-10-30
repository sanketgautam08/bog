package com.sanketgauatm.bog.repo.room;

import com.sanketgauatm.bog.dto.RoomDto;
import com.sanketgauatm.bog.model.Room;
import com.sanketgauatm.bog.model.Status;

import java.util.List;
import java.util.Optional;

public interface CustomRoomRepo {
    Optional<RoomDto> createRoom(Room room);
    Integer getRoomCapacity(Integer roomId);
    List<RoomDto> getAllRooms();
    boolean validateCapacityAndStatus(Room room);
    Optional<Status> getRoomStatus(Integer roomId);
}
