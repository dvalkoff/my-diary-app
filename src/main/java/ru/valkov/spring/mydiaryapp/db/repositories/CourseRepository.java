package ru.valkov.spring.mydiaryapp.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valkov.spring.mydiaryapp.db.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
