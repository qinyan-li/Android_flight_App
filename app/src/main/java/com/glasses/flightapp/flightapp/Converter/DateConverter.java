package com.glasses.flightapp.flightapp.Converter;

import android.arch.persistence.room.TypeConverter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateConverter {
    @TypeConverter
    public static ZonedDateTime fromString(String value) {
        return value.equals("") ? null : ZonedDateTime.ofInstant(Instant.parse(value), ZoneId.of("GMT"));
    }

    @TypeConverter
    public static String dateToString(ZonedDateTime date) {
        return date == null ? "" : date.toInstant().toString();
    }
}
