package com.sanketgauatm.bog.repo.user;

import com.sanketgauatm.bog.dto.UserDto;

import java.util.List;

public interface CustomUserRepo {
    List<UserDto> getAllUsers();
}
