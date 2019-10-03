package com.github.manedev79.timesheet.adapters.secondary.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.MonthDay;
import java.time.YearMonth;
import java.util.Arrays;

@Configuration
public class MongoConverterConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new YearMonthToStringConverter(),
                new StringToYearMonthConverter(),
                new MonthDayToStringConverter(),
                new StringToMonthDayConverter()
        ));
    }

    @WritingConverter
    static class YearMonthToStringConverter implements Converter<YearMonth, String> {
        @Override
        public String convert(YearMonth source) {
            return source.toString();
        }
    }

    @ReadingConverter
    static class StringToYearMonthConverter implements Converter<String, YearMonth> {
        @Override
        public YearMonth convert(String source) {
            return YearMonth.parse(source);
        }
    }

    @WritingConverter
    static class MonthDayToStringConverter implements Converter<MonthDay, String> {
        @Override
        public String convert(MonthDay monthDay) {
            return monthDay.toString();
        }
    }

    @ReadingConverter
    static class StringToMonthDayConverter implements Converter<String, MonthDay> {
        @Override
        public MonthDay convert(String s) {
            return MonthDay.parse(s);
        }
    }


}
