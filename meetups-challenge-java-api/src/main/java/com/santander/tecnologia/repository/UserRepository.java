package com.santander.tecnologia.repository;

import com.santander.tecnologia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findByIdIn(Set<Long> userIds);
}
