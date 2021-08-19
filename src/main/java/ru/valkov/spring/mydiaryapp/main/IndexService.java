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
import java.util.Optional;

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

    @Transactional
    public void unsubscribeUserFromCourse(String courseTitle) {
        Course course = getCourseByTitle(courseTitle);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((AppUser)principal).getUsername();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(username);

        course.removeSubscriber(appUser);
        appUser.removeSubscription(course);

        appUserService.saveUser(appUser);
        courseService.saveCourse(course);

    }

    public void createNewCourse(CourseDetailsRequest courseDetailsRequest) {
        Optional<Course> unexpectedCourse = courseService.findCourseByTitle(courseDetailsRequest.getTitle());
        if (unexpectedCourse.isPresent()) {
            throw new IllegalStateException(String.format("Course with title %s already exists", courseDetailsRequest.getTitle()));
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((AppUser)principal).getUsername();
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(username);

        Course course = new Course(
                courseDetailsRequest.getTitle(),
                courseDetailsRequest.getDescription(),
                appUser
        );

        courseService.saveCourse(course);
    }
}
