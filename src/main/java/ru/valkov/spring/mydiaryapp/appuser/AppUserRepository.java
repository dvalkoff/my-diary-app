package ru.valkov.spring.mydiaryapp.appuser;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.valkov.spring.mydiaryapp.main.entities.Course;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser " + "SET isEnabled = ?2 " + "WHERE id = ?1")
    void updateEnabledById(Long id, boolean state);
}
