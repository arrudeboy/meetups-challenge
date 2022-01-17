package com.santander.tecnologia;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:application.properties")
@EnableRetry
@EnableHystrix
@EnableCaching
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class MeetupsChallengeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MeetupsChallengeApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MeetupsChallengeApplication.class);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HttpHeaders contentTypeJsonHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Bean
    public Keycloak keycloakInstance(@Value("${keycloak.auth-server-url}") String url,
                                     @Value("${keycloak.realm}") String realm,
                                     @Value("${admin.keycloak.client.id}") String clientId,
                                     @Value("${admin.keycloak.client.secret}") String clientSecret,
                                     @Value("${admin.keycloak.client.user}") String user,
                                     @Value("${admin.keycloak.client.password}") String password) {

        return KeycloakBuilder.builder()
                .serverUrl(url)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(user)
                .password(password)
                .build();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("retrieveWeather", "getMeetupBeerPacksNeeded");
    }

}
