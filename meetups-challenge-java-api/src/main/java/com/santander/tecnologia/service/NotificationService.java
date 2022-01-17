package com.santander.tecnologia.service;

import org.springframework.scheduling.annotation.Async;

public interface NotificationService {

    @Async
    void send(String[] to, String subject, String text);
}
