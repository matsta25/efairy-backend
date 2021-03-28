package com.matsta25.efairy.model;

import java.util.HashMap;

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

    private static final HashMap<String, ZodiacSign> MAP = new HashMap<String, ZodiacSign>();

    public final String label;

    private ZodiacSign(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ZodiacSign getByLabel(String label) {
        return MAP.get(label);
    }

    static {
        for (ZodiacSign field : ZodiacSign.values()) {
            MAP.put(field.getLabel(), field);
        }
    }

    @Override
    public String toString() {
        return "ZodiacSign{" +
                "label='" + label + '\'' +
                '}';
    }
}
