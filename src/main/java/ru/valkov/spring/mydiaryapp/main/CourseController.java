package ru.valkov.spring.mydiaryapp.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.valkov.spring.mydiaryapp.main.entities.Course;
import ru.valkov.spring.mydiaryapp.main.entities.Lesson;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(
        path = "/courses"
)
public class CourseController {

    private final IndexService indexService;

    @Autowired
    public CourseController(IndexService indexService) {
        this.indexService = indexService;
    }


    @GetMapping
    public String getAllCoursesView(@RequestParam(value = "page", required = false) Integer page, Model model) {
        if (page == null) page = 1;
        page--;
        PageRequest pageable = PageRequest.of(page, 5);

        Page<Course> paging = indexService.getCoursesPageable(pageable);
        List<Integer> pages = IntStream.rangeClosed(1, paging.getTotalPages()).boxed().collect(Collectors.toList());

        model.addAttribute("courses", paging);
        model.addAttribute("pages", pages);

        return "courses";
    }

    @GetMapping("/{courseTitle}")
    public String getCourse(@PathVariable("courseTitle") String courseTitle, Model model) {
        Course course = indexService.getCourseByTitle(courseTitle);
        model.addAttribute("course", course);
        List<Lesson> lessons = indexService.getLessonsByCourse(course);
        model.addAttribute("lessons", lessons);
        return "course";
    }


    @GetMapping("/{courseTitle}/subscribe")
    public String subscribeToCourse(@PathVariable("courseTitle") String courseTitle, Model model) {
        try {
            indexService.subscribeUserToCourse(courseTitle);
            return "redirect:/courses";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
    }
}
