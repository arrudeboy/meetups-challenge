package com.santander.tecnologia.dto;

import javax.validation.constraints.NotBlank;

public class CreateLocationDTO {

    @NotBlank
    private String countryCode;

    @NotBlank
    private String city;

    public CreateLocationDTO() {}

    public CreateLocationDTO(@NotBlank String countryCode, @NotBlank String city) {
        this.countryCode = countryCode;
        this.city = city;
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
}
