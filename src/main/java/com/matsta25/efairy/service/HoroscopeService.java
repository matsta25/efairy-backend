package com.matsta25.efairy.service;

import com.matsta25.efairy.model.Horoscope;
import com.matsta25.efairy.model.ZodiacSign;
import com.matsta25.efairy.repository.HoroscopeRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class HoroscopeService {

    HoroscopeRepository horoscopeRepository;

    public HoroscopeService(HoroscopeRepository horoscopeRepository) {
        this.horoscopeRepository = horoscopeRepository;
    }

    public Horoscope getHoroscope(ZodiacSign zodiacSignProcessed) {
        // TODO: handle optional better way
        return this.horoscopeRepository
                .findFirstByZodiacSignAndDate(zodiacSignProcessed.getLabel(), LocalDate.now())
                .orElse(null);
    }
}
