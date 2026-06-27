package com.hackthon.security.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class JwtRequest {
    private String email;
    private String password;
}
