package com.hang.config.utils;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @USER: zzh-
 * @DATE: 2022/4/21
 * @TIME: 13:44
 */
@Component
public class DateConverterUtils implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
