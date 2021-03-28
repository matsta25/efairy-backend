package com.matsta25.efairy.config;

import com.matsta25.efairy.model.Horoscope;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class HoroscopeFieldSetMapper implements FieldSetMapper<Horoscope> {

    @Override
    public Horoscope mapFieldSet(FieldSet fieldSet) {
        final Horoscope horoscope = new Horoscope();

//        TODO: move somewhere those mappers - another class
        horoscope.setZodiacSign(fieldSet.readString("zodiacSign"));
        horoscope.setDate(fieldSet.readDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        horoscope.setContent(fieldSet.readString("content"));

        return horoscope;
    }
}
