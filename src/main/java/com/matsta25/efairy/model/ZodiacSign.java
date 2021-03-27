package com.matsta25.efairy.model;

public enum ZodiacSign {
    virgo("panna"),
    libra("waga"),
    scorpio("skorpion"),
    sagittarius("strzelec"),
    capricorn("koziorozec"),
    aquarius("wodnik"),
    pisces("ryby"),
    aries("baran"),
    taurus("byk"),
    gemini("bliznieta"),
    cancer("rak"),
    leo("lew");


    public final String label;

    private ZodiacSign(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
