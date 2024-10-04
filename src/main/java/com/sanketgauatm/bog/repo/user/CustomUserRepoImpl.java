package com.sanketgauatm.bog.repo.user;

import com.sanketgauatm.bog.dto.UserDto;
import com.sanketgauatm.bog.mapper.UserDtoMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomUserRepoImpl implements CustomUserRepo {

    private final JdbcClient jdbcClient;

    public CustomUserRepoImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return jdbcClient.sql("select * from users").query(new UserDtoMapper()).list();
    }
}
