package ru.valkov.spring.mydiaryapp.main.requests;

import java.time.LocalDateTime;

public class LessonDetailsRequest {
    private String theme;
    private String description;
    private String homework;
    private String startsAt;
    private String endsAt;

    public LessonDetailsRequest(String theme, String description, String homework, String startsAt, String endsAt) {
        this.theme = theme;
        this.description = description;
        this.homework = homework;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public LessonDetailsRequest() {
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

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }
}
