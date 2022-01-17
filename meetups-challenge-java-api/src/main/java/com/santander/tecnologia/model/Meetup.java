package com.santander.tecnologia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meetups")
public class Meetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "meetup")
    @JsonIgnore
    private Set<UserMeetup> users = new HashSet<>();

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @Column(name = "meetup_date")
    private String date;

    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$")
    @Column(name = "meetup_time")
    private String time;

    @Column(columnDefinition = "varchar(255) default 'No description'")
    private String description;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Meetup() {}

    public Meetup(@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String date,
                  @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$") String time,
                  String description, Location location) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserMeetup> getUsers() {
        return users;
    }

    public void setUsers(Set<UserMeetup> users) {
        this.users = users;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Meetup{" +
                "id=" + id +
                ", users=" + users +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", location=" + location +
                '}';
    }
}
