package com.sanketgauatm.bog.mapper;

import com.sanketgauatm.bog.dto.RoomDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDtoMapper implements RowMapper<RoomDto> {
    @Override
    public RoomDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RoomDto(
                rs.getInt("room_id"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getInt("max_capacity"),
                rs.getInt("created_by_user_id")
        );
    }
}
