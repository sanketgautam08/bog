package com.sanketgauatm.bog.repo.feedback;

import com.sanketgauatm.bog.dto.FeedBackDto;

import java.util.List;
import java.util.Optional;

public interface CustomFeedbackRepo {
    List<FeedBackDto> getAllUserFeedbacks(int userId);
    Optional<List<FeedBackDto>> getAllFeedbacksForConference(int conferenceId);
}
