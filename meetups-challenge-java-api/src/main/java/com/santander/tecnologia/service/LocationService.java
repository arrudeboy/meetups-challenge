package com.santander.tecnologia.service;

import com.santander.tecnologia.dto.CreateLocationDTO;
import com.santander.tecnologia.dto.UpdateLocationDTO;
import com.santander.tecnologia.model.Location;

import java.util.List;

public interface LocationService {

    List<Location> getAll();
    Location add(CreateLocationDTO createLocationDTO);
    Location update(UpdateLocationDTO updateLocationDTO);
    void delete(Long id);
}
