package com.hackthon.dto;

import lombok.Data;

@Data
public class PersonneLoginRequestDto {
    private String usernameOrEmail;

    private String password;
}
