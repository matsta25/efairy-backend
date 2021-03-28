package com.matsta25.efairy.repository;

import com.matsta25.efairy.model.Horoscope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HoroscopeRepository extends JpaRepository<Horoscope, Long> {

    Optional<Horoscope> getHoroscopeByZodiacSignAndDate(String  zodiacSign, LocalDate date);
}
