package com.santander.tecnologia.helper;

import com.santander.tecnologia.dto.WeatherApiResponseDTO;
import com.santander.tecnologia.exception.WeatherReportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class WeatherApiClientHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiClientHelper.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders contentTypeJsonHttpHeaders;

    private String getEncodedURL(String rawURL) {
        return rawURL.replaceAll(" ", "%20");
    }

    @Retryable(maxAttempts = 2, include = WeatherReportException.class, backoff = @Backoff(delay = 400, multiplier = 2))
    public WeatherApiResponseDTO doRequest(String url, Class<? extends WeatherApiResponseDTO> responseDtoClass) throws WeatherReportException {

        try {
            LOGGER.info("Requesting weather through API Weather service...");
            HttpEntity<String> request = new HttpEntity<>("", contentTypeJsonHttpHeaders);
            ResponseEntity<? extends WeatherApiResponseDTO> response = restTemplate.exchange(
                    URI.create(getEncodedURL(url)),
                    HttpMethod.GET,
                    request,
                    responseDtoClass);

            LOGGER.info("Getting response from API Weather service...");
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            String msg = String.format("Could not retrieve weather: %s", e.getMessage());
            LOGGER.warn(msg);
            LOGGER.info("Retrying...");
            throw new WeatherReportException(msg);
        }
    }

    @Recover
    public WeatherApiResponseDTO unavailableWeatherApiService(WeatherReportException exception, String url) throws WeatherReportException {
        String msg = "Unavailable Weather API service, please try later";
        LOGGER.error(msg);
        throw new WeatherReportException(msg);
    }

}