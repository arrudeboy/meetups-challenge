package com.santander.tecnologia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenWeatherMapDailyForecastDTO {

    @JsonProperty("temp")
    private Double tempCelsius;

    @JsonProperty("temp_min")
    private Double tempMin;

    @JsonProperty("temp_max")
    private Double tempMax;

    public OpenWeatherMapDailyForecastDTO() {
    }

    public OpenWeatherMapDailyForecastDTO(Double tempCelsius, Double tempMin, Double tempMax) {
        this.tempCelsius = tempCelsius;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    public Double getTempCelsius() {
        return tempCelsius;
    }

    public void setTempCelsius(Double tempCelsius) {
        this.tempCelsius = tempCelsius;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }
}
