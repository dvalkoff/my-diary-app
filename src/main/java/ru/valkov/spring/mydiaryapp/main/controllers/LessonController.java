package ru.valkov.spring.mydiaryapp.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.valkov.spring.mydiaryapp.appuser.AppUser;
import ru.valkov.spring.mydiaryapp.main.entities.Course;
import ru.valkov.spring.mydiaryapp.main.requests.LessonDetailsRequest;
import ru.valkov.spring.mydiaryapp.main.services.CourseService;
import ru.valkov.spring.mydiaryapp.main.services.IndexService;
import ru.valkov.spring.mydiaryapp.main.services.LessonService;

@Controller
public class LessonController {
    private final IndexService indexService;
    private final CourseService courseService;
    private final LessonService lessonService;

    @Autowired
    public LessonController(IndexService indexService, CourseService courseService, LessonService lessonService) {
        this.indexService = indexService;
        this.courseService = courseService;
        this.lessonService = lessonService;
    }

    @GetMapping("/courses/{courseTitle}/create-lesson")
    public String getNewLessonView(@PathVariable("courseTitle") String courseTitle, Model model) {

        Course course = indexService.getCourseByTitle(courseTitle);
        model.addAttribute("course", course);
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (course.getOwner().getUsername().equals(user.getUsername())) {
            return "lesson/create-lesson";
        } else {
            model.addAttribute("error", "access denied");
            return "error-page";
        }
    }

    @PostMapping("/courses/{courseTitle}/create-lesson")
    public String createNewLesson(@PathVariable("courseTitle") String courseTitle, LessonDetailsRequest request) {
        Course course = indexService.getCourseByTitle(courseTitle);
        lessonService.createNewLesson(course, request);
        return "redirect:/courses/" + courseTitle;
    }
}
