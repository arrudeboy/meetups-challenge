package com.santander.tecnologia.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "weather_reports")
public class WeatherReport {
    // https://www.youtube.com/watch?v=pqashW66D7o :D
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    @Column
    private String date;

    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$")
    @Column
    private String time;

    @Column
    private double temperature;

    @Column(name = "temperature_max")
    private double temperatureMax;

    @Column(name = "temperature_min")
    private double temperatureMin;

    @NotBlank
    @Column
    private String countryCode;

    @NotBlank
    @Column
    private String city;

    public WeatherReport() {
    }

    public WeatherReport(@NotNull String date, @NotNull String time, double temperature, double temperatureMax,
                         double temperatureMin, @NotBlank String countryCode, @NotBlank String city) {
        this.date = date;
        this.time = time;
        this.temperature = temperature;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.countryCode = countryCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
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

    @Override
    public String toString() {
        return "WeatherReport{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", temperature=" + temperature +
                ", temperatureMax=" + temperatureMax +
                ", temperatureMin=" + temperatureMin +
                ", countryCode='" + countryCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

