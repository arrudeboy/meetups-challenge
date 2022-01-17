package com.santander.tecnologia.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.santander.tecnologia.dto.OpenWeatherMapForecastDTO;
import com.santander.tecnologia.dto.OpenWeatherMapResponseDTO;
import com.santander.tecnologia.dto.WeatherApiResponseDTO;
import com.santander.tecnologia.exception.WeatherApiResponseParsingException;
import com.santander.tecnologia.exception.WeatherReportException;
import com.santander.tecnologia.helper.WeatherApiClientHelper;
import com.santander.tecnologia.model.Location;
import com.santander.tecnologia.model.WeatherReport;
import com.santander.tecnologia.repository.WeatherReportRepository;
import com.santander.tecnologia.service.WeatherReportService;
import com.santander.tecnologia.util.LocalDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@PropertySource("classpath:application.properties")
@Service
public class OpenWeatherMapServiceImpl implements WeatherReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherMapServiceImpl.class);

    @Value("${api.openweathermap.base.url}")
    private String weatherApiBaseUrl;

    @Value("${api.openweathermap.app.id}")
    private String weatherApiAppId;

    @Autowired
    private WeatherApiClientHelper weatherApiClientHelper;

    @Autowired
    private WeatherReportRepository weatherReportRepository;

    private OpenWeatherMapForecastDTO searchForecast(List<OpenWeatherMapForecastDTO> forecastDTOList, String date, String time) {

        OpenWeatherMapForecastDTO result = forecastDTOList.get(0); // it's the default value if not found
        LocalDate localDateMeetup = LocalDateUtil.getFromString(date);
        Set<OpenWeatherMapForecastDTO> meetupDateForecasts = forecastDTOList
                .stream()
                .filter(f -> localDateMeetup.equals(LocalDateUtil.getFromString(f.getDate())))
                .collect(Collectors.toSet());

        int hourMeetup = Integer.parseInt(time.split(":")[0]);
        int minHourDiff = 25;
        for (OpenWeatherMapForecastDTO openWeatherMapForecastDTO : meetupDateForecasts) {
            String fDate = openWeatherMapForecastDTO.getDate();
            String fHour = fDate.split(":")[0].substring(fDate.length() - 2);
            int hour = Integer.parseInt(fHour);
            if (hour <= hourMeetup && hourMeetup - hour < minHourDiff) {
                result = openWeatherMapForecastDTO;
                minHourDiff = hourMeetup - hour;
            }
        }
        ;

        return result;
    }

    @Override
    public void save(WeatherReport weatherReport) {

        Optional<WeatherReport> weatherReportOptional =
                weatherReportRepository.findByDateAndCountryCodeAndCity(weatherReport.getDate(), weatherReport.getCountryCode(), weatherReport.getCity());
        if (weatherReportOptional.isPresent()) {
            WeatherReport existentWeatherReport = weatherReportOptional.get();
            existentWeatherReport.setTime(weatherReport.getTime());
            existentWeatherReport.setTemperature(weatherReport.getTemperature());
            existentWeatherReport.setTemperatureMax(weatherReport.getTemperatureMax());
            existentWeatherReport.setTemperatureMin(weatherReport.getTemperatureMin());
            weatherReportRepository.save(existentWeatherReport);
            LOGGER.info(String.format("Updated. %s", existentWeatherReport.toString()));
        } else {
            weatherReportRepository.save(weatherReport);
            LOGGER.info(String.format("Saved new. %s", weatherReport.toString()));
        }
    }

    @Override
    @Cacheable("retrieveWeather")
    @HystrixCommand(fallbackMethod = "getFromDatabase", commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "false")
    })
    public Optional<WeatherReport> retrieve(Location location, String date, String time) throws WeatherReportException {

        try {
            String queryUrl = weatherApiBaseUrl + String.format("%s,%s&appid=%s&units=metric", location.getCity(), location.getCountryCode(), weatherApiAppId);
            WeatherApiResponseDTO weatherApiResponseDTO = weatherApiClientHelper.doRequest(queryUrl, OpenWeatherMapResponseDTO.class);
            OpenWeatherMapResponseDTO openWeatherMapResponseDTO = parseWeatherApiResponse(weatherApiResponseDTO);
            OpenWeatherMapForecastDTO resultForecast = searchForecast(openWeatherMapResponseDTO.getForecastList(), date, time);
            String resultForecastTime = resultForecast.getDate().split(" ")[1];
            WeatherReport result = new WeatherReport(
                    date, resultForecastTime,
                    resultForecast.getDailyForecast().getTempCelsius(),
                    resultForecast.getDailyForecast().getTempMax(),
                    resultForecast.getDailyForecast().getTempMin(),
                    location.getCountryCode(), location.getCity());

            save(result);
            return Optional.of(result);
        } catch (WeatherApiResponseParsingException weatherApiResponseParsingException) {
            String msg = String.format("Failed parsing API response: %s", weatherApiResponseParsingException.getMessage());
            LOGGER.error(msg);
            throw new WeatherReportException(msg);
        }
    }

    @Override
    public Optional<WeatherReport> getFromDatabase(Location location, String date, String time) throws WeatherReportException {

        return weatherReportRepository
                .findByDateAndCountryCodeAndCity(date, location.getCountryCode(), location.getCity());
    }

    @Override
    public OpenWeatherMapResponseDTO parseWeatherApiResponse(WeatherApiResponseDTO weatherApiResponseDTO) throws WeatherApiResponseParsingException {

        if (weatherApiResponseDTO instanceof OpenWeatherMapResponseDTO) {
            return (OpenWeatherMapResponseDTO) weatherApiResponseDTO;
        } else {
            throw new WeatherApiResponseParsingException();
        }
    }

}
