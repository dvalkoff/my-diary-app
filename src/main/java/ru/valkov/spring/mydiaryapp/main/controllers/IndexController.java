package ru.valkov.spring.mydiaryapp.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.valkov.spring.mydiaryapp.appuser.AppUser;
import ru.valkov.spring.mydiaryapp.main.services.IndexService;
import ru.valkov.spring.mydiaryapp.main.entities.Course;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class IndexController {

    private final IndexService indexService;

    @Autowired
    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping
    public String getIndexView(Model model) {
        try {
            model.addAttribute("lessons", indexService.getUsersLesson());
            model.addAttribute("formatter", "%02d");
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/subscriptions")
    public String getSubscriptionsView(@RequestParam(name = "page", required = false) Integer page, Model model) {
        try {
            if (page == null) page = 1;
            page--;
            PageRequest pageable = PageRequest.of(page, 5);

            Page<Course> paging = indexService.getUserSubscriptionsPageable(pageable);
            List<Integer> pages = IntStream.rangeClosed(1, paging.getTotalPages()).boxed().collect(Collectors.toList());
            AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            model.addAttribute("user", user);
            model.addAttribute("courses", paging);
            model.addAttribute("pages", pages);
            model.addAttribute("subscriptions", indexService.getUserSubscriptions());

            return "course/subscriptions";
        } catch (ClassCastException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
}
