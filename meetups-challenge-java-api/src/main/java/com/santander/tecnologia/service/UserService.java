package com.santander.tecnologia.service;

import com.santander.tecnologia.dto.UserAddDTO;
import com.santander.tecnologia.dto.UserUpdateDTO;
import com.santander.tecnologia.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Long id);

    User add(UserAddDTO userAddDTO);

    User update(UserUpdateDTO userUpdateDTO);

    void delete(Long id);
}
