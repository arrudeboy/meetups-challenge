package com.santander.tecnologia.dto;

import javax.validation.constraints.*;

public class UserUpdateDTO {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    @Size(min = 10, max = 50)
    private String password;

    @NotBlank
    @Email
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
