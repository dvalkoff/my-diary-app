package ru.valkov.spring.mydiaryapp.main.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.valkov.spring.mydiaryapp.appuser.AppUser;
import ru.valkov.spring.mydiaryapp.main.entities.Course;
import ru.valkov.spring.mydiaryapp.main.repositories.CourseRepository;

import java.util.List;
import java.util.Optional;


@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllUsersCourses(AppUser appUser) {
        return courseRepository.findAllBySubscribers(appUser);
    }

    public Page<Course> getCoursesPageable(PageRequest pageable) {
        return courseRepository.findAll(pageable);
    }

    public Course getCourseByTitle(String title) {
        return courseRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalStateException(String.format("Course with title %s not found", title)));
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> findCourseByTitle(String courseTitle) {
        return courseRepository.findByTitle(courseTitle);
    }
}
