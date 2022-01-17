package com.santander.tecnologia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "locations", uniqueConstraints = {@UniqueConstraint(columnNames = {"country_code", "city"})})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotBlank
    @Column(name = "country_code")
    private String countryCode;

    @NotBlank
    @Column
    private String city;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private Set<Meetup> meetups = new HashSet<>();

    public Location() {}

    public Location(@NotBlank String countryCode, @NotBlank String city) {
        this.countryCode = countryCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Meetup> getMeetups() {
        return meetups;
    }

    public void setMeetups(Set<Meetup> meetups) {
        this.meetups = meetups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return countryCode.equals(location.countryCode) &&
                city.equals(location.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, city);
    }
}
