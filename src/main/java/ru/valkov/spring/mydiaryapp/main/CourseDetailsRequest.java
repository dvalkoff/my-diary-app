package ru.valkov.spring.mydiaryapp.main;

public class CourseDetailsRequest {
    private String title;
    private String description;

    public CourseDetailsRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public CourseDetailsRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
