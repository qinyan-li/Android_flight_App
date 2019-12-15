package com.glasses.flightapp.flightapp.Converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Arrays;

public class ArrayConverter {

    @TypeConverter
    public static double[] fromString(String value) {
        String[] stringValues = value.substring(1, value.length() - 2).split(",\\s");
        return Arrays.stream(stringValues).mapToDouble(Double::parseDouble).toArray();
    }

    @TypeConverter
    public static String fromArray(double[] array) {
        return Arrays.toString(array);
    }
}
