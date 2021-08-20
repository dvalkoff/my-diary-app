package ru.valkov.spring.mydiaryapp.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.valkov.spring.mydiaryapp.appuser.AppUser;
import ru.valkov.spring.mydiaryapp.main.entities.Course;
import ru.valkov.spring.mydiaryapp.main.entities.Lesson;
import ru.valkov.spring.mydiaryapp.main.repositories.LessonRepository;
import ru.valkov.spring.mydiaryapp.main.requests.LessonDetailsRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> findUsersLessons(AppUser appUser) {
        return lessonRepository.findAllByCourse_SubscribersOrderByStartsAt(appUser);
    }

    public List<Lesson> getLessonsByCourse(Course course) {
        return lessonRepository.findAllByCourse(course);
    }

    public void createNewLesson(Course course, LessonDetailsRequest request) {
        Lesson lesson = new Lesson(
                course,
                request.getTheme(),
                request.getDescription(),
                request.getHomework(),
                LocalDateTime.parse(request.getStartsAt()),
                LocalDateTime.parse(request.getEndsAt())
        );

        lessonRepository.save(lesson);
    }
}
