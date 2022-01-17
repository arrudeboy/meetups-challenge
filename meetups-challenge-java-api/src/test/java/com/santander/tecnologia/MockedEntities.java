package com.santander.tecnologia;

import com.santander.tecnologia.dto.OpenWeatherMapCityDTO;
import com.santander.tecnologia.dto.OpenWeatherMapDailyForecastDTO;
import com.santander.tecnologia.dto.OpenWeatherMapForecastDTO;
import com.santander.tecnologia.dto.OpenWeatherMapResponseDTO;
import com.santander.tecnologia.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MockedEntities {

    public static List<User> getUsers() {

        User user1 = new User("arturo.chari", "1234567890", "arturofelixchari@gmail.com");
        user1.setId(1L);
        User user2 = new User("felix.chari", "1234567890", "arturo.chari@ibm.com");
        user2.setId(2L);
        return Arrays.asList(user1, user2);
    }

    public static User getInvalidUser() {

        User invalidUser = new User();
        invalidUser.setEmail("malformed Email . / ");
        invalidUser.setPassword("short-psw");
        invalidUser.setUsername("a");

        return invalidUser;
    }
    public static Meetup getMeetup() {

        Meetup meetup = new Meetup("2022-01-16", "19:40:00", "Test meetup", getLocations().get(0));
        meetup.setId(1L);
        return meetup;
    }

    public static List<Location> getLocations() {

        Location location1 = new Location("AR", "Buenos Aires");
        location1.setId(1L);
        Location location2 = new Location("UY", "Montevideo");
        location2.setId(2L);
        return Arrays.asList(location1, location2);
    }

    public static Optional<WeatherReport> getWeatherReport() {
        return Optional.of(new WeatherReport(
                "2022-01-16",
                "16:00:00",
                25.0,
                29.3,
                16.6,
                "AR",
                "Buenos Aires"
        ));
    }

    public static UserMeetup getUserMeetup() {
        UserMeetup userMeetup = new UserMeetup(getMeetup(), getUsers().get(1), false);
        userMeetup.setId(1L);

        return userMeetup;
    }

    public static List<UserMeetup> getUserMeetups() {
        return null;
    }

    public static OpenWeatherMapResponseDTO getWeatherApiResponseDTO() {

        OpenWeatherMapDailyForecastDTO dailyForecastDTO = new OpenWeatherMapDailyForecastDTO(25.3, 15.5, 30.0);
        OpenWeatherMapForecastDTO forecastDTO = new OpenWeatherMapForecastDTO(null, dailyForecastDTO, "2022-01-16 15:00:00");
        OpenWeatherMapCityDTO cityDTO = new OpenWeatherMapCityDTO("Buenos Aires", "AR");
        return new OpenWeatherMapResponseDTO(null, null, Arrays.asList(forecastDTO), cityDTO);
    }
}