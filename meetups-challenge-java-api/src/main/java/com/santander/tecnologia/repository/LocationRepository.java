package com.santander.tecnologia.repository;

import com.santander.tecnologia.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
