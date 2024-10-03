package com.sanketgauatm.bog.repo.room;

import com.sanketgauatm.bog.model.Room;
import com.sanketgauatm.bog.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CustomRoomRepoImpl implements CustomRoomRepo {

    private final JdbcClient jdbcClient;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomRoomRepoImpl.class);

    public CustomRoomRepoImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Integer getRoomCapacity(Integer roomId) {
        String sql = "SELECT max_capacity FROM rooms WHERE room_id = :roomId";
        try{
            return jdbcClient.sql(sql).params(Map.of("roomId", roomId)).query(Integer.class).single();
        }catch(Exception e){
            LOGGER.error("Error while checking room_capacity\n{}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean validateCapacityAndStatus(Room room) {
        int prevCapacity = getRoomCapacity(room.getId());
        if(prevCapacity > room.getMaxCapacity()){
            String sql = """
                    select Max(v) from (
                        select room_id, count(room_id) as v
                        from conference
                        where room_id = ?
                        group by room_id)
                    """;
            int maxAttendees = jdbcClient.sql(sql).param(room.getId()).query(Integer.class).single();
            if(maxAttendees > room.getMaxCapacity()){
                LOGGER.error("max_caacity smaller than number of attendees");
                return false;
            }
        }
        if(room.getStatus().equals(Status.UNDER_CONSTRUCTION)){
            boolean isConferenceDue = jdbcClient.sql("Select count(1) from conference where room_id = ? and date_time > now() ").param(room.getId()).query(Integer.class).single() > 0 ;
            if(isConferenceDue){
                LOGGER.error("Room can't be under construction. There are pending conference(s) in the room.");
                return false;
            }
        }
        return true;
    }







}