package ru.valkov.spring.mydiaryapp.main.services;

import org.springframework.stereotype.Service;
import ru.valkov.spring.mydiaryapp.appuser.AppUser;
import ru.valkov.spring.mydiaryapp.main.entities.Course;
import ru.valkov.spring.mydiaryapp.main.entities.Lesson;
import ru.valkov.spring.mydiaryapp.main.repositories.LessonRepository;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> findUsersLessons(AppUser appUser) {
        return lessonRepository.findAllByCourse_SubscribersOrderByStartAt(appUser);
    }

    public List<Lesson> getLessonsByCourse(Course course) {
        return lessonRepository.findAllByCourse(course);
    }
}
