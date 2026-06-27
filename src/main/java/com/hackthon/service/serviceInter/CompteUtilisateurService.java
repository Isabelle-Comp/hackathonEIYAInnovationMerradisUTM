package com.hackthon.service.serviceInter;

import com.hackthon.dto.*;

public interface CompteUtilisateurService {

    SaveUserDto saveUtilisateur(RegisterDto registerDto);
    PersonneDto deactivateAccounts(Long id);
    PersonneDto activateAccounts(Long id);
    String motPasswordOublier( ForgotPasswordRequestdto forgotPasswordRequestdto);
    String ChangePassword(ChangePasswordDto changePasswordDto);
    String RecupererCompte(ResetPasswordDto resetPasswordDto);

}
