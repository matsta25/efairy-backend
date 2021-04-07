package com.matsta25.efairy.repository;

import com.matsta25.efairy.model.Horoscope;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoroscopeRepository extends JpaRepository<Horoscope, Long> {

    //    Optional<Horoscope> getHoroscopeByZodiacSignAndDate(String zodiacSign, LocalDate date);

    Optional<Horoscope> findFirstByZodiacSignAndDate(String zodiacSign, LocalDate date);
}
