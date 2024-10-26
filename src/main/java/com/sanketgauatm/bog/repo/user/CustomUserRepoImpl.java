package com.sanketgauatm.bog.repo.user;

import com.sanketgauatm.bog.dto.UserDto;
import com.sanketgauatm.bog.mapper.UserDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomUserRepoImpl implements CustomUserRepo {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomUserRepoImpl.class);
    private final JdbcClient jdbcClient;

    public CustomUserRepoImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<List<UserDto>> getAllUsers() {
        try{
            return Optional.of(jdbcClient.sql("select * from users").query(new UserDtoMapper()).list());
        }catch(Exception e){
            LOGGER.error("Error getting all users\n{}", e.getMessage());
            return Optional.empty();
        }
    }
}
