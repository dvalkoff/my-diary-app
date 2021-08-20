package ru.valkov.spring.mydiaryapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.valkov.spring.mydiaryapp.appuser.AppUser;
import ru.valkov.spring.mydiaryapp.appuser.AppUserRepository;
import ru.valkov.spring.mydiaryapp.appuser.AppUserRole;
import ru.valkov.spring.mydiaryapp.main.entities.Course;
import ru.valkov.spring.mydiaryapp.main.entities.Lesson;
import ru.valkov.spring.mydiaryapp.main.repositories.CourseRepository;
import ru.valkov.spring.mydiaryapp.main.repositories.LessonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MyDiaryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDiaryAppApplication.class, args);
	}


}
