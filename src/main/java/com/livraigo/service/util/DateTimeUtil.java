package com.livraigo.service.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    
    public LocalTime parseTime(String timeString) {
        return LocalTime.parse(timeString, TIME_FORMATTER);
    }
    
    public String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
    
    public boolean isWithinTimeWindow(LocalTime time, LocalTime start, LocalTime end) {
        return !time.isBefore(start) && !time.isAfter(end);
    }
}