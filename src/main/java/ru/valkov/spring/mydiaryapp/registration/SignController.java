package ru.valkov.spring.mydiaryapp.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignController {
    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }


    @PostMapping("/sign-up")
    public String registerUser(@RequestBody AppUserDetailsRequest appUserDetailsRequest) {
        signService.registerUser(appUserDetailsRequest);
        return "";
    }
}
