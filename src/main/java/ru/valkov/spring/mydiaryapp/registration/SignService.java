package ru.valkov.spring.mydiaryapp.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.valkov.spring.mydiaryapp.appuser.AppUser;
import ru.valkov.spring.mydiaryapp.appuser.AppUserRole;
import ru.valkov.spring.mydiaryapp.appuser.AppUserService;

@Service
public class SignService {
    private final AppUserService appUserService;

    @Autowired
    public SignService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    public void registerUser(AppUserDetailsRequest request) throws IllegalStateException {
        AppUser appUser = new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.STUDENT,
                true,
                true,
                true,
                false
        );

        appUserService.createNewUser(appUser);
    }

    public void confirmAnAccount(String token) throws IllegalStateException{
        appUserService.confirmAnAccount(token);
    }

}
