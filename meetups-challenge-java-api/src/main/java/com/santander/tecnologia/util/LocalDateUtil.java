package com.santander.tecnologia.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDate getFromString(String localDate) {
        return LocalDate.parse(localDate.substring(0, 10));
    }

    public static String getFromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER);
    }
}
