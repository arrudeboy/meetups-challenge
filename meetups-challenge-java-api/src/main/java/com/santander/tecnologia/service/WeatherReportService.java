package com.santander.tecnologia.service;

import com.santander.tecnologia.dto.WeatherApiResponseDTO;
import com.santander.tecnologia.exception.WeatherApiResponseParsingException;
import com.santander.tecnologia.exception.WeatherReportException;
import com.santander.tecnologia.model.Location;
import com.santander.tecnologia.model.WeatherReport;

import java.util.Optional;

public interface WeatherReportService {

    void save(WeatherReport weatherReport);

    Optional<WeatherReport> retrieve(Location location, String date, String time) throws WeatherReportException;

    Optional<WeatherReport> getFromDatabase(Location location, String date, String time) throws WeatherReportException;

    <T extends WeatherApiResponseDTO> WeatherApiResponseDTO parseWeatherApiResponse(WeatherApiResponseDTO weatherApiResponseDTO) throws WeatherApiResponseParsingException;
}
