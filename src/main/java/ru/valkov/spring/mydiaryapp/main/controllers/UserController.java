package ru.valkov.spring.mydiaryapp.main.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.valkov.spring.mydiaryapp.appuser.AppUser;
import ru.valkov.spring.mydiaryapp.appuser.AppUserService;
import ru.valkov.spring.mydiaryapp.main.services.IndexService;
import ru.valkov.spring.mydiaryapp.main.entities.Course;

import java.util.List;

@Controller
public class UserController {
    private final IndexService indexService;
    private final AppUserService appUserService;

    public UserController(IndexService indexService, AppUserService appUserService) {
        this.indexService = indexService;
        this.appUserService = appUserService;
    }


    @GetMapping("/me")
    public String getMyAccountView(Model model) {
        try {
            AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Course> courses = indexService.getUserOwnCourses(user);
            model.addAttribute("appUser", user);
            model.addAttribute("courses", courses);

            return "my-account";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/users/{username}")
    public String getUserInfoView(@PathVariable("username") String username, Model model) {
        try {
            AppUser currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            AppUser foundUser = (AppUser) appUserService.loadUserByUsername(username);
            if (currentUser.getUsername().equals(foundUser.getUsername())) {
                return "redirect:/me";
            }
            List<Course> courses = indexService.getUserOwnCourses(foundUser);
            model.addAttribute("foundUser", foundUser);
            model.addAttribute("subscriptions", indexService.getUserSubscriptions());
            model.addAttribute("courses", courses);

            return "user-page";

        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }

    }
}
