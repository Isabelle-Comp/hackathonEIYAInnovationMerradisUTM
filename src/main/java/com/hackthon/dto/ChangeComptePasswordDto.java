package com.hackthon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeComptePasswordDto {
    String nouveauComptePassword;
    String ConfirmNouveauPassword;
}
