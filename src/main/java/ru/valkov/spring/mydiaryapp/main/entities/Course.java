package ru.valkov.spring.mydiaryapp.main.entities;

import ru.valkov.spring.mydiaryapp.appuser.AppUser;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Course {
    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long id;


    @Column(
            unique = true,
            nullable = false
    )
    private String title;

    @ManyToOne
    @JoinColumn(
            name = "owner_id",
            nullable = false
    )
    private AppUser owner;

    @ManyToMany
    @JoinTable(
            name = "user_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> subscribers;

    public Course(String title, AppUser owner) {
        this.owner = owner;
        this.title = title;
        this.subscribers = Set.of();
    }

    public Course() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<AppUser> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<AppUser> subscribers) {
        this.subscribers = subscribers;
    }

    public void addSubscriber(AppUser appUser) {
        subscribers.add(appUser);
    }

    public void removeSubscriber(AppUser appUser) {
        subscribers.remove(appUser);
    }

    @Transient
    public Integer getCountSubscribers() {
        return subscribers.size();
    }
}
