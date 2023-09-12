package br.com.luciano.registrationemail.registration;

import br.com.luciano.registrationemail.appuser.AppUser;
import br.com.luciano.registrationemail.appuser.AppUserRole;
import br.com.luciano.registrationemail.appuser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("Email not valid");
        }
        AppUser appUser = new AppUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), AppUserRole.USER);
        return appUserService.signUpUser(appUser);
    }
}
