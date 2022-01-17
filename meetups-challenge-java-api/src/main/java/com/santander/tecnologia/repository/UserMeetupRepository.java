package com.santander.tecnologia.repository;

import com.santander.tecnologia.model.UserMeetup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMeetupRepository extends JpaRepository<UserMeetup, Long> {
}
