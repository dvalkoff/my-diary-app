package ru.valkov.spring.mydiaryapp.email;

public interface EmailSender {
    void sendEmail(String to, String email);
}
