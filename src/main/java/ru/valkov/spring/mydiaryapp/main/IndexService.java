package ru.valkov.spring.mydiaryapp.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.valkov.spring.mydiaryapp.appuser.AppUser;
import ru.valkov.spring.mydiaryapp.appuser.AppUserService;
import ru.valkov.spring.mydiaryapp.main.entities.Course;
import ru.valkov.spring.mydiaryapp.main.entities.Lesson;
import ru.valkov.spring.mydiaryapp.main.services.CourseService;
import ru.valkov.spring.mydiaryapp.main.services.LessonService;

import java.util.List;

@Service
public class IndexService {
    private final CourseService courseService;
    private final LessonService lessonService;
    private final AppUserService appUserService;

    public IndexService(CourseService courseService, LessonService lessonService, AppUserService appUserService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.appUserService = appUserService;
    }

    public List<Lesson> getUsersLesson() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = ((AppUser)principal).getUsername();

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(username);

        List<Lesson> lessons = lessonService.findUsersLessons(appUser);

        return lessons;

    }

    public List<Course> getUserSubscriptions() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = ((AppUser)principal).getUsername();

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(username);

        List<Course> courses = courseService.getAllUsersCourses(appUser);

        return courses;
    }

    public Page<Course> getCoursesPageable(PageRequest pageable) {
         return courseService.getCoursesPageable(pageable);
    }

    public Course getCourseByTitle(String courseTitle) throws IllegalStateException {
        return courseService.getCourseByTitle(courseTitle);
    }

    public List<Lesson> getLessonsByCourse(Course course) {
        return lessonService.getLessonsByCourse(course);
    }

    @Transactional
    public void subscribeUserToCourse(String courseTitle) throws IllegalStateException{

        Course course = getCourseByTitle(courseTitle);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((AppUser)principal).getUsername();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(username);

        course.addSubscriber(appUser);
        appUser.addSubscription(course);

        AppUser updatedUser = appUserService.saveUser(appUser);
        Course updatedCourse = courseService.saveCourse(course);

        return;
    }
}
