package com.matsta25.efairy.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Horoscope {

    @Id
    private long id;

    @Column(name = "zodiac_sign")
    @Enumerated(EnumType.STRING)
    private ZodiacSign zodiacSign;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "content")
    private String content;

    public Horoscope() {
    }

    public long getId() {
        return id;
    }

    public ZodiacSign getZodiacSign() {
        return zodiacSign;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setZodiacSign(ZodiacSign zodiacSign) {
        this.zodiacSign = zodiacSign;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
