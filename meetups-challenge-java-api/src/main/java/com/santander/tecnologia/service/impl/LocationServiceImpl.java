package com.santander.tecnologia.service.impl;

import com.santander.tecnologia.dto.CreateLocationDTO;
import com.santander.tecnologia.dto.UpdateLocationDTO;
import com.santander.tecnologia.model.Location;
import com.santander.tecnologia.model.User;
import com.santander.tecnologia.repository.LocationRepository;
import com.santander.tecnologia.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);

    private static final String LOCATION_NOT_FOUND_MSG = "No location with id %d exists!";

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> getAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location add(CreateLocationDTO createLocationDTO) {
        Location location =
                locationRepository.saveAndFlush(new Location(createLocationDTO.getCountryCode(), createLocationDTO.getCity()));

        LOGGER.info(String.format("Saved new. %s", location.toString()));
        return location;
    }

    @Override
    public Location update(UpdateLocationDTO updateLocationDTO) {
        Long id = updateLocationDTO.getId();
        Optional<Location> locationOptional = locationRepository.findById(id);
        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();
            location.setCountryCode(updateLocationDTO.getCountryCode());
            location.setCity(updateLocationDTO.getCity());
            locationRepository.saveAndFlush(location);

            LOGGER.info(String.format("Updated location. %s", location.toString()));
            return location;
        } else {
            throw new EntityNotFoundException(
                    String.format(LOCATION_NOT_FOUND_MSG, id));
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            locationRepository.deleteById(id);
            LOGGER.info(String.format("Location with id %d has been deleted", id));
        } else {
            throw new EntityNotFoundException(
                    String.format(LOCATION_NOT_FOUND_MSG, id));
        }
    }

}
