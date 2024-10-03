package com.sanketgauatm.bog.controller;

import com.sanketgauatm.bog.dto.FeedBackDto;
import com.sanketgauatm.bog.model.User;
import com.sanketgauatm.bog.repo.feedback.FeedbackRepo;
import com.sanketgauatm.bog.repo.user.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);
    private final UserRepo userRepo;
    private final FeedbackRepo feedbackRepo;

    public FeedbackController(UserRepo userRepo, FeedbackRepo feedbackRepo) {
        this.userRepo = userRepo;
        this.feedbackRepo = feedbackRepo;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<FeedBackDto>> getFeedback(@PathVariable int userId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()) {
            List<FeedBackDto> feedbacks = feedbackRepo.getAllUserFeedbacks(userId);
            return ResponseEntity.ok(feedbacks);
        }else{
            LOGGER.error("User not found with id {}", userId);
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/conference/{id}")
    public ResponseEntity<List<FeedBackDto>> getAllConferenceFeedback(@PathVariable int id) {
        try{
            return ResponseEntity.ok(feedbackRepo.getAllFeedbacksForConference(id));
        }catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }
    }

}
