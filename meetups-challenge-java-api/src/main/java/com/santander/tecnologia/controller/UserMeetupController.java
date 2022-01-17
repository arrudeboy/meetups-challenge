package com.santander.tecnologia.controller;

import com.santander.tecnologia.dto.UserMeetupAddDTO;
import com.santander.tecnologia.service.UserMeetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("meetupUsers")
public class UserMeetupController {

    @Autowired
    private UserMeetupService userMeetupService;

    @PostMapping(value = "/enroll", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> enroll(@Valid @RequestBody UserMeetupAddDTO userMeetupAddDTO) {
        return ResponseEntity.ok(userMeetupService.enroll(userMeetupAddDTO));
    }

    @PutMapping(value = "/{id}/checkIn", produces = {"application/json"})
    public ResponseEntity<?> checkIn(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userMeetupService.checkIn(id));
    }

}
