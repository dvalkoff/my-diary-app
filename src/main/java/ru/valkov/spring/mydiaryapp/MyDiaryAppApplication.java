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

	private final AppUserRepository appUserRepository;
	private final CourseRepository courseRepository;
	private final LessonRepository lessonRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public MyDiaryAppApplication(AppUserRepository appUserRepository, CourseRepository courseRepository, LessonRepository lessonRepository, PasswordEncoder passwordEncoder) {
		this.appUserRepository = appUserRepository;
		this.courseRepository = courseRepository;
		this.lessonRepository = lessonRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(MyDiaryAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			AppUser appUser1 = new AppUser(
					"Dima",
					"Valkov",
					"dmivalkov@gmail.com",
					passwordEncoder.encode("password"),
					AppUserRole.STUDENT,
					true,
					true,
					true,
					true
			);

			AppUser appUser2 = new AppUser(
					"Arina",
					"Kalaighan",
					"arinageek@gmail.com",
					passwordEncoder.encode("password"),
					AppUserRole.STUDENT,
					true,
					true,
					true,
					true
			);

			AppUser appUser3 = new AppUser(
					"Ksenya",
					"Denishenkova",
					"ksenideni@gmail.com",
					passwordEncoder.encode("password"),
					AppUserRole.STUDENT,
					true,
					true,
					true,
					true
			);

			Course dimaCourse = new Course(
					"math",
					appUser1
			);

			Course dimaCourse1 = new Course(
					"math1",
					appUser2
			);
			Course dimaCourse2 = new Course(
					"math2",
					appUser3
			);
			Course dimaCourse3 = new Course(
					"math3",
					appUser1
			);Course dimaCourse4 = new Course(
					"math4",
					appUser2
			);Course dimaCourse5 = new Course(
					"math5",
					appUser3
			);Course dimaCourse6 = new Course(
					"math6",
					appUser1
			);Course dimaCourse7 = new Course(
					"math7",
					appUser2
			);Course dimaCourse8 = new Course(
					"math8",
					appUser3
			);Course dimaCourse9 = new Course(
					"math9",
					appUser1
			);Course dimaCourse10 = new Course(
					"math10",
					appUser2
			);Course dimaCourse11 = new Course(
					"math11",
					appUser3
			);Course dimaCourse12 = new Course(
					"math12",
					appUser1
			);


			Lesson lesson1 = new Lesson(
					dimaCourse,
					"Integrals 1",
					LocalDateTime.now(),
					LocalDateTime.now().plusMinutes(45)
			);

			lesson1.setHomework("Page 45-49 #11.5-12.3");
			lesson1.setDescription("Integral essentials for everybody");

			Lesson lesson2 = new Lesson(
					dimaCourse,
					"Integrals 2",
					LocalDateTime.now().plusMinutes(500),
					LocalDateTime.now().plusMinutes(545)
			);


			lesson2.setDescription("Integral for advanced");
			lesson2.setHomework("Page 50-52 #12.4-12.10");




			dimaCourse.setSubscribers(Set.of(appUser2, appUser3));

			appUserRepository.saveAll(List.of(appUser1, appUser2, appUser3));
			courseRepository.saveAll(List.of(dimaCourse, dimaCourse2, dimaCourse3, dimaCourse4, dimaCourse5, dimaCourse6, dimaCourse7, dimaCourse8, dimaCourse9, dimaCourse10, dimaCourse11, dimaCourse12));
			lessonRepository.saveAll(List.of(lesson1, lesson2));


		};
	}

}
