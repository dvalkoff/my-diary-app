package ru.valkov.spring.mydiaryapp.db.services;

import org.springframework.stereotype.Service;
import ru.valkov.spring.mydiaryapp.db.repositories.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}
