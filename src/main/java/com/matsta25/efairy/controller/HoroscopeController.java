package com.matsta25.efairy.controller;

import com.matsta25.efairy.model.Horoscope;
import com.matsta25.efairy.service.HoroscopeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/horoscope")
public class HoroscopeController {

    HoroscopeService horoscopeService;

    public HoroscopeController(HoroscopeService horoscopeService) {
        this.horoscopeService = horoscopeService;
    }

    @GetMapping
    public Horoscope getDailyHoroscope() {
        return this.horoscopeService.getHoroscope();
    }
}