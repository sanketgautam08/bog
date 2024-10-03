package com.sanketgauatm.bog.repo.room;

import com.sanketgauatm.bog.model.Room;

public interface CustomRoomRepo {
    Integer getRoomCapacity(Integer roomId);

    boolean validateCapacityAndStatus(Room room);
}
