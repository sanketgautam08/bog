package com.sanketgauatm.bog.repo.conference;

import com.sanketgauatm.bog.model.Conference;
import com.sanketgauatm.bog.model.User;

public interface CustomRepo {
    String insertIntoConferenceUsers(int id, User user);
    Integer countRegisteredUsers(int id);
    boolean updateConferenceDetails(Conference conference);
    void insertIntoConfirmation(String confirmationNumber, int conferenceId, int userId);
    boolean deleteConference(int conferenceId);

}
