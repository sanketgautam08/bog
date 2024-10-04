package com.sanketgauatm.bog.mapper;

import com.sanketgauatm.bog.dto.FeedBackDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeedbackDtoMapper implements RowMapper<FeedBackDto> {
    @Override
    public FeedBackDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FeedBackDto(
          rs.getInt("conference_id"),
                rs.getString("feedback_text"),
                rs.getInt("rating"),
                rs.getString("user_name")
                );
    }
}
