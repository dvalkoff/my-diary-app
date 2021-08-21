package ru.valkov.spring.mydiaryapp.main.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Lesson {
    @Id
    @SequenceGenerator(
            name = "lesson_sequence",
            sequenceName = "lesson_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "lesson_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "course_id",
            nullable = false
    )
    private Course course;

    @Column(nullable = false)
    private String theme;

    @Column(
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            columnDefinition = "TEXT"
    )
    private String homework;

    @Column(nullable = false)
    private LocalDateTime startsAt;

    @Column(nullable = false)
    private LocalDateTime endsAt;

    public Lesson(Course course, String theme, LocalDateTime startsAt, LocalDateTime endsAt) {
        this.course = course;
        this.theme = theme;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public Lesson(Course course, String theme, String description, String homework, LocalDateTime startsAt, LocalDateTime endsAt) {
        this.course = course;
        this.theme = theme;
        this.description = description;
        this.homework = homework;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public Lesson() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startAt) {
        this.startsAt = startAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }
}
