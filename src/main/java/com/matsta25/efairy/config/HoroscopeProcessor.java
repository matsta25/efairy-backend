package com.matsta25.efairy.config;

import com.matsta25.efairy.model.Horoscope;
import java.time.LocalDate;
import org.springframework.batch.item.ItemProcessor;

public class HoroscopeProcessor implements ItemProcessor<Horoscope, Horoscope> {

    @Override
    public Horoscope process(Horoscope horoscope) {
        final String zodiacSign = horoscope.getZodiacSign();
        final LocalDate date = horoscope.getDate();
        final String content = horoscope.getContent();

        Horoscope processedHoroscope = new Horoscope();
        processedHoroscope.setZodiacSign(zodiacSign);
        processedHoroscope.setDate(date);
        processedHoroscope.setContent(content);

        return processedHoroscope;
    }
}
