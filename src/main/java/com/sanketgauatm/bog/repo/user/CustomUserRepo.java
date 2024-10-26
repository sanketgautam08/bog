package com.sanketgauatm.bog.repo.user;

import com.sanketgauatm.bog.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface CustomUserRepo {
    Optional<List<UserDto>> getAllUsers();
}
