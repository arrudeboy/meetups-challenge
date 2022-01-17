package com.santander.tecnologia.repository;

import com.santander.tecnologia.model.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {

    @Query(value = "SELECT * FROM meetups m WHERE to_date(m.meetup_date, 'YYYY-MM-DD') >= current_date;", nativeQuery = true)
    Set<Meetup> getIncomingMeetups();
}
