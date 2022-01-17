package com.santander.tecnologia.controller;

import com.santander.tecnologia.dto.CreateLocationDTO;
import com.santander.tecnologia.dto.UpdateLocationDTO;
import com.santander.tecnologia.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(locationService.getAll());
    }

    @PostMapping(value = "/add", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> add(@Valid @RequestBody CreateLocationDTO createLocationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.add(createLocationDTO));
    }

    @PutMapping(value = "/update", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> update(@Valid @RequestBody UpdateLocationDTO updateLocationDTO) {
        return ResponseEntity.ok(locationService.update(updateLocationDTO));
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        locationService.delete(id);
        String jsonMessage = String
                .format("{\"message\": \"Location with id %d has been deleted\"}", id);

        return ResponseEntity.ok(jsonMessage);
    }


}
