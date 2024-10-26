package com.sanketgauatm.bog.repo.conference;

import com.sanketgauatm.bog.dto.ConferenceDto;
import com.sanketgauatm.bog.mapper.ConferenceDtoMapper;
import com.sanketgauatm.bog.model.Conference;
import com.sanketgauatm.bog.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Repository
public class CustomRepoImpl implements CustomRepo {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomRepoImpl.class);
    private final JdbcClient jdbcClient;

    public CustomRepoImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public String insertIntoConferenceUsers(int id, User user) {
        String sql = """
                insert into conference_users(conference_id, user_id)
                values(:conferenceId, :userId)
                """;
        try{
            jdbcClient.sql(sql).params(Map.of("conferenceId", id, "userId", user.getId())).update();
            LOGGER.info("Inserted user into conference_users");
            boolean insertedConfirmation = false;
            String randomstring = "";
            while(!insertedConfirmation){
                randomstring = generateRandomString();
                insertIntoConfirmation(randomstring, id, user.getId());
                insertedConfirmation = true;
            }
            return randomstring;
        }catch(Exception e){
            LOGGER.error("Error while inserting into conference_user\n{}", e.getMessage());
            return null;
        }
    }

    @Override
    public Integer countRegisteredUsers(int id) {
        return jdbcClient.sql("select count(1) from conference_users where conference_id = ?").param(id).query(Integer.class).single();
    }

    @Override
    public boolean updateConferenceDetails(Conference conference) {
        String sql = """
                update conference
                set name=:name, description= :description2, room_id = :roomId, start_date_time= :startDateTime, end_date_time= :endDateTime
                where conference_id = :conferenceId
                """;
        try {
            jdbcClient.sql(sql).params(Map.of(
                    "name", conference.getName(),
                    "description", conference.getDescription(),
                    "roomId", conference.getRoom().getId(),
                    "startDateTime", conference.getStartDateTime(),
                    "endDateTime", conference.getEndDateTime(),
                    "conferenceId", conference.getId()
            )).update();
            LOGGER.info("Updated conference details");
            return true;
        }catch(Exception e){
            LOGGER.error("Error while updating conference details\n{}", e.getMessage());
            return false;
        }
    }

    @Override
    public void insertIntoConfirmation(String confirmationNumber, int conferenceId, int userId) {
        String sql = """
                insert into confirmation(confirmation_number, conference_id, user_id)
                values(?,?,?)
                """;
        jdbcClient.sql(sql).params(confirmationNumber,conferenceId, userId).update();
        LOGGER.info("Confirmation added to db.");
    }
    @Override
    public boolean deleteConference(int conferenceId) {
        boolean hasConferenceHappened = hasConferenceHappened(conferenceId);
        if(hasConferenceHappened){
            return false;
        }
        boolean isDeletedFromConUser = deleteFromConferenceUsers(conferenceId);
        boolean isDeletedFromConfirmation = deleteFromConfirmation(conferenceId);
        if(isDeletedFromConUser && isDeletedFromConfirmation){
            try{
                jdbcClient.sql("delete from conference where conference_id = ?").param(conferenceId).update();
                return true;
            } catch (Exception e) {
                LOGGER.error("Error while deleting conference\n{}", e.getMessage()) ;
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public List<ConferenceDto> getAllConferences() {
        return jdbcClient.sql("select * from conference").query(new ConferenceDtoMapper()).list();
    }

    @Override
    public boolean checkConferenceAvailability(Conference conference) {
        String sql = """
                select count(1) from conference c
                where room_id = :roomId
                and (start_date_time  between :startDateTime and :endDateTime
                or end_date_time  between :startDateTime and :endDateTime
                or :startDateTime between start_date_time and end_date_time
                or :endDateTime between start_date_time and end_date_time)
                """;

        try{
            int count =  jdbcClient.sql(sql)
                    .params(Map.of("roomId", conference.getRoom().getId(),"startDateTime", conference.getStartDateTime(), "endDateTime", conference.getEndDateTime()))
                    .query(Integer.class).single();
            return count == 0;
        }catch(Exception e){
            LOGGER.error("Error while checking conference availability\n{}", e.getMessage());
            return false;
        }
    }

    @Override
    public int generateNewId() {
        return jdbcClient.sql("select nextval('conference_seq')").query(Integer.class).single();
    }

    @Override
    public Optional<ConferenceDto> insertIntoConference(Conference conference) {
        String sql = """
                INSERT INTO conference(conference_id, start_date_time, end_date_time, room_id, description, name) VALUES
                (?,?,?,?,?,?)
                """;
        try{
            boolean added = jdbcClient.sql(sql).params(
                    conference.getId(),
                    conference.getStartDateTime(),
                    conference.getEndDateTime(),
                    conference.getRoom().getId(),
                    conference.getDescription(),
                    conference.getName()
            ).update() == 1 ;
            if(added){
                return Optional.of(new ConferenceDto(conference.getId(), conference.getName(), conference.getDescription(), conference.getRoom().getId(), conference.getStartDateTime(), conference.getEndDateTime()));
            }else{
                return Optional.empty();
            }
        }catch (Exception e){
            LOGGER.error("Error while inserting into conference\n{}", e.getMessage());
            return Optional.empty();
        }
    }

    private boolean hasConferenceHappened(int conferenceId) {
        String sql = """
                select count(1) from conference where
                conference_id = ?
                and start_date_time < current_date
                """;
        return jdbcClient.sql(sql).params(conferenceId).query(Integer.class).single() > 0;

    }

    private boolean deleteFromConfirmation(int conferenceId) {
        String sql = """
                delete from confirmation where conference_id = ?
                """;
        try{
            jdbcClient.sql(sql).param(conferenceId).update();
            LOGGER.info("Deleted from confirmation with id {}", conferenceId);
            return true;
        }catch(Exception e){
            LOGGER.error("Error while deleting from confirmation\n{}", e.getMessage());
            return false;
        }
    }

    private boolean deleteFromConferenceUsers(int id) {
        String sql = """
                delete from conference_users where conference_id = :conferenceId
                """;
        try{
            jdbcClient.sql(sql).params(Map.of("conferenceId", id)).update();
            LOGGER.info("Deleted from conference_user with id {}", id);
            return true;
        }catch(Exception e){
            LOGGER.error("Error while deleting from conference_user\n{}", e.getMessage());
            return false;
        }
    }

    private String generateRandomString(){
        String alphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        Random random = new Random();
        StringBuilder s = new StringBuilder(8);

        for(int i = 0; i < 8; i++) {
            int position = random.nextInt(alphaNumericStr.length());
            s.append(alphaNumericStr.charAt(position));
        }
        return s.toString();
    }
}
