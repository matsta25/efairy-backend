package com.matsta25.efairy.service;

import com.matsta25.efairy.model.Horoscope;
import com.matsta25.efairy.model.ZodiacSign;
import com.matsta25.efairy.repository.HoroscopeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HoroscopeService {

    HoroscopeRepository horoscopeRepository;

    public HoroscopeService(HoroscopeRepository horoscopeRepository) {
        this.horoscopeRepository = horoscopeRepository;
    }

    public Horoscope getHoroscope() {
        // TODO: handle optional better way
        return this.horoscopeRepository.getHoroscopeByZodiacSignAndDate(ZodiacSign.virgo.getLabel(), LocalDate.now()).orElse(null);
    }
}
