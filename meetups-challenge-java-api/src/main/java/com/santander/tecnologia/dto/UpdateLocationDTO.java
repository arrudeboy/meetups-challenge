package com.santander.tecnologia.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateLocationDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String city;

    public UpdateLocationDTO() {}

    public UpdateLocationDTO(@NotNull Long id, @NotBlank String countryCode, @NotBlank String city) {
        this.id = id;
        this.countryCode = countryCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
