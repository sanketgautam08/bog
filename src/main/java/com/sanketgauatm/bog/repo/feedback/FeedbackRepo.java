package com.sanketgauatm.bog.repo.feedback;

import com.sanketgauatm.bog.model.Feedback;
import org.springframework.data.repository.ListCrudRepository;

public interface FeedbackRepo extends ListCrudRepository<Feedback, Integer>, CustomFeedbackRepo {
}
