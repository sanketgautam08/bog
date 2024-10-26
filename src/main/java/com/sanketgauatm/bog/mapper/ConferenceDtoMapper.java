package com.sanketgauatm.bog.mapper;

import com.sanketgauatm.bog.dto.ConferenceDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConferenceDtoMapper implements RowMapper<ConferenceDto> {
    @Override
    public ConferenceDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ConferenceDto(
                rs.getInt("conference_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("room_id"),
                rs.getTimestamp("start_date_time").toLocalDateTime(),
                rs.getTimestamp("end_date_time").toLocalDateTime()
                );
    }
}
