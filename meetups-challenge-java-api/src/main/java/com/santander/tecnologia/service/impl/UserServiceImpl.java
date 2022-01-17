package com.santander.tecnologia.service.impl;

import com.santander.tecnologia.dto.UserAddDTO;
import com.santander.tecnologia.dto.UserUpdateDTO;
import com.santander.tecnologia.helper.KeycloakApiClientHelper;
import com.santander.tecnologia.model.User;
import com.santander.tecnologia.repository.UserRepository;
import com.santander.tecnologia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_NOT_FOUND_MSG = "No user with id %d exists!";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeycloakApiClientHelper keycloakApiClientHelper;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format(USER_NOT_FOUND_MSG, id)));
    }

    @Override
    public User add(UserAddDTO userAddDTO) {

        User user = userRepository
                .saveAndFlush(new User(userAddDTO.getUsername(), userAddDTO.getPassword(), userAddDTO.getEmail()));

        keycloakApiClientHelper.createUser(user);
        LOGGER.info(String.format("Saved new. %s", user));
        return user;
    }

    @Override
    public User update(UserUpdateDTO userUpdateDTO) {
        Long id = userUpdateDTO.getId();
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(userUpdateDTO.getPassword());
            user.setEmail(userUpdateDTO.getEmail());
            userRepository.saveAndFlush(user);

            keycloakApiClientHelper.updateUser(user);
            LOGGER.info(String.format("Updated user. %s", user));
            return user;
        } else {
            throw new EntityNotFoundException(
                    String.format(USER_NOT_FOUND_MSG, id));
        }
    }

    @Override
    public void delete(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            String username = optionalUser.get().getUsername();
            keycloakApiClientHelper.deleteUser(username);
            LOGGER.info(String.format("Updated user with username: %s", username));
        } else {
            throw new EntityNotFoundException(
                    String.format(USER_NOT_FOUND_MSG, id));
        }
    }

}
