package com.santander.tecnologia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenWeatherMapForecastDTO {

    @JsonProperty("dt")
    private String dt;

    @JsonProperty("main")
    private OpenWeatherMapDailyForecastDTO dailyForecast;

    @JsonProperty("dt_txt")
    private String date;

    public OpenWeatherMapForecastDTO() {}

    public OpenWeatherMapForecastDTO(String dt, OpenWeatherMapDailyForecastDTO dailyForecast, String date) {
        this.dt = dt;
        this.dailyForecast = dailyForecast;
        this.date = date;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public OpenWeatherMapDailyForecastDTO getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(OpenWeatherMapDailyForecastDTO dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
