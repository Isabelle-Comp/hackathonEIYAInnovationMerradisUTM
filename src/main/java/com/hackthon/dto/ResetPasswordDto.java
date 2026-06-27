package com.hackthon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
    String mail;
    String nouveauComptePassword;
    String ConfirmNouveauPassword;
}
