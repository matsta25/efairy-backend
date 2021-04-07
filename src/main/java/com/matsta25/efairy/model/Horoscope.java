package com.matsta25.efairy.model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
public class Horoscope {

    //    TODO: find out witch annotations are really necessary

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "zodiac_sign", nullable = false)
    private String zodiacSign;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    // TODO: find out best length value
    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    public Horoscope() {}

    public Horoscope(String zodiacSign, LocalDate date, String content) {
        this.zodiacSign = zodiacSign;
        this.date = date;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getZodiacSign() {
        return zodiacSign;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setZodiacSign(String zodiacSign) {
        this.zodiacSign = zodiacSign;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Horoscope{"
                + "id="
                + id
                + ", zodiacSign="
                + zodiacSign
                +
                //                ", zodiacSign=" + zodiacSign.toString() +
                ", date="
                + date
                + ", content='"
                + content
                + '\''
                + '}';
    }
}
