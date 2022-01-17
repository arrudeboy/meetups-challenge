package com.santander.tecnologia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenWeatherMapResponseDTO extends WeatherApiResponseDTO {

    private String code;

    private String cnt;

    @JsonProperty("list")
    private List<OpenWeatherMapForecastDTO> forecastList;

    @JsonProperty("city")
    private OpenWeatherMapCityDTO city;

    public OpenWeatherMapResponseDTO() {
    }

    public OpenWeatherMapResponseDTO(String code, String cnt, List<OpenWeatherMapForecastDTO> forecastList, OpenWeatherMapCityDTO city) {
        this.code = code;
        this.cnt = cnt;
        this.forecastList = forecastList;
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public List<OpenWeatherMapForecastDTO> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<OpenWeatherMapForecastDTO> forecastList) {
        this.forecastList = forecastList;
    }

    public OpenWeatherMapCityDTO getCity() {
        return city;
    }

    public void setCity(OpenWeatherMapCityDTO city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "OpenWeatherMapResponseDTO{" +
                "code='" + code + '\'' +
                ", cnt='" + cnt + '\'' +
                ", forecastList=" + forecastList +
                ", city=" + city +
                '}';
    }
}
