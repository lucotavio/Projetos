package br.com.luciano.registrationemail.registration;

import br.com.luciano.registrationemail.appuser.AppUser;
import br.com.luciano.registrationemail.appuser.AppUserRole;
import br.com.luciano.registrationemail.appuser.AppUserService;
import br.com.luciano.registrationemail.registration.token.ConfirmationToken;
import br.com.luciano.registrationemail.registration.token.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("Email not valid");
        }
        AppUser appUser = new AppUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), AppUserRole.USER);
        return appUserService.signUpUser(appUser);
    }

    @Transactional
    public String confirmToken(String token){

        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());

        return "Confirmed";
    }
}
