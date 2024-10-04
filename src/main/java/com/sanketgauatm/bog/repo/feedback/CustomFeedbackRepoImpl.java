package com.sanketgauatm.bog.repo.feedback;

import com.sanketgauatm.bog.dto.FeedBackDto;
import com.sanketgauatm.bog.mapper.FeedbackDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomFeedbackRepoImpl implements CustomFeedbackRepo  {
    private final JdbcClient jdbcClient;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomFeedbackRepoImpl.class);

    public CustomFeedbackRepoImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<FeedBackDto> getAllUserFeedbacks(int userId) {
        String sql = "Select * from feedback where user_id=?";
        return jdbcClient.sql(sql).params(userId).query(new FeedbackDtoMapper()).list();

    }

    @Override
    public List<FeedBackDto> getAllFeedbacksForConference(int conferenceId) {
        String sql = """
                select * from feedback
                where conference_id = ?
                """;
        return jdbcClient.sql(sql).param(conferenceId).query(new FeedbackDtoMapper()).list();
    }

}
