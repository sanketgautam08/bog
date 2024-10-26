package com.sanketgauatm.bog.repo.feedback;

import com.sanketgauatm.bog.dto.FeedBackDto;
import com.sanketgauatm.bog.mapper.FeedbackDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<List<FeedBackDto>> getAllFeedbacksForConference(int conferenceId) {
        String sql = """
                select * from feedback
                where conference_id = ?
                """;
        try{
            return Optional.of(jdbcClient.sql(sql).param(conferenceId).query(new FeedbackDtoMapper()).list());
        }catch(Exception e){
            LOGGER.error("Error while getting user feedbacks\n{}",e.getMessage());
            return Optional.empty();
        }
    }

}
