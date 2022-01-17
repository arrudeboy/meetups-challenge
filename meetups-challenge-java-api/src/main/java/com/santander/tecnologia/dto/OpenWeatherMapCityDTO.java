package com.santander.tecnologia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenWeatherMapCityDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private String country;

    public OpenWeatherMapCityDTO() {
    }

    public OpenWeatherMapCityDTO(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
