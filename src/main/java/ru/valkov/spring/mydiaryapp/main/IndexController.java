package ru.valkov.spring.mydiaryapp.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/subscriptions")
    public String getSubscriptionsView(Model model) {
        try {
            model.addAttribute("courses", indexService.getUserSubscriptions());
            return "course/subscriptions";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
}
