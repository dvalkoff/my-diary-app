package ru.valkov.spring.mydiaryapp.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignController {
    private final SignService signService;

    @Autowired
    public SignController(SignService signService) {
        this.signService = signService;
    }


    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String getRegisterView() {
        return "/registration/sign-up";
    }

    @GetMapping("/sign-up/confirm")
    public String confirmAnAccount(@RequestParam("token") String token, Model model) {
        try {
            signService.confirmAnAccount(token);
            return "/registration/account-activated";
        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @PostMapping("/sign-up")
    public String registerUser(Model model, AppUserDetailsRequest appUserDetailsRequest) {
        try {
            signService.registerUser(appUserDetailsRequest);
            model.addAttribute("name", appUserDetailsRequest.getFirstName());
            return "/registration/confirm-email";
        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }

    }

}
