package com.santander.tecnologia.model;

import javax.persistence.*;

@Entity
@Table(name = "meetup_users", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "meetup_id"})})
public class UserMeetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meetup_id")
    private Meetup meetup;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "check_in")
    private boolean checkIn = false;

    public UserMeetup() {}

    public UserMeetup(Meetup meetup, User user, boolean checkIn) {
        this.meetup = meetup;
        this.user = user;
        this.checkIn = checkIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meetup getMeetup() {
        return meetup;
    }

    public void setMeetup(Meetup meetup) {
        this.meetup = meetup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    @Override
    public String toString() {
        return "UserMeetup{" +
                "id=" + id +
                ", meetup_id=" + meetup.getId() +
                ", user_id=" + user.getId() +
                ", checkIn=" + checkIn +
                '}';
    }
}
