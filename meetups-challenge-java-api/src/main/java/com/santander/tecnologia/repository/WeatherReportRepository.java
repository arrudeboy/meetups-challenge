package com.santander.tecnologia.repository;

import com.santander.tecnologia.model.WeatherReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherReportRepository extends JpaRepository<WeatherReport, Long> {

    Optional<WeatherReport> findByDateAndCountryCodeAndCity(String date, String countryCode, String city);
}
