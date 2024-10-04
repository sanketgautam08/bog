package com.sanketgauatm.bog.dto;

import com.sanketgauatm.bog.model.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private int userId;
    private String firstName;
    private String lastName;
    private Gender gender;

    public UserDto(int userId, String firstName, String lastName, String gender) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = Gender.valueOf(gender);
    }
}
