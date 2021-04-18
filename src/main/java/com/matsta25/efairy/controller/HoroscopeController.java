package com.matsta25.efairy.controller;

import com.matsta25.efairy.model.Horoscope;
import com.matsta25.efairy.model.ZodiacSign;
import com.matsta25.efairy.service.HoroscopeService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/horoscope")
public class HoroscopeController {

    HoroscopeService horoscopeService;

    public HoroscopeController(HoroscopeService horoscopeService) {
        this.horoscopeService = horoscopeService;
    }

    @GetMapping
    public Horoscope getDailyHoroscope(@RequestParam(required = true) String zodiacSign) {
        ZodiacSign zodiacSignProcessed = ZodiacSign.valueOf(zodiacSign);
        return this.horoscopeService.getHoroscope(zodiacSignProcessed);
    }
}
