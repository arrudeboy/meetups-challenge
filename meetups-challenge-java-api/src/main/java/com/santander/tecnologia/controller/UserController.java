package com.santander.tecnologia.controller;

import com.santander.tecnologia.dto.UserAddDTO;
import com.santander.tecnologia.dto.UserUpdateDTO;
import com.santander.tecnologia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping(value = "/add", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> add(@Valid @RequestBody UserAddDTO userAddDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(userAddDTO));
    }

    @PutMapping(value = "/update", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.update(userUpdateDTO));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        userService.delete(id);
        String jsonMessage = String
                .format("{\"message\": \"User with id %d has been deleted\"}", id);

        return ResponseEntity.ok(jsonMessage);
    }

}
