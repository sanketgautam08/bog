package com.sanketgauatm.bog.repo.feedback;

import com.sanketgauatm.bog.dto.FeedBackDto;

import java.util.List;

public interface CustomFeedbackRepo {
    List<FeedBackDto> getAllUserFeedbacks(int userId);
    List<FeedBackDto> getAllFeedbacksForConference(int conferenceId);
}
