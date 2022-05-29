package com.example.application.backend.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StringUtil {
    public static final String euroSymbol = "€";
    public static String FirstNotNullOrEmpty(String ...strings) {
        String output = null;

        for (String string:strings) {
            if (!isEmptyOrNull(string))
                output = string;
        }
        return output;
    }

    public static boolean isEmptyOrNull(String string) {
        return string == null || string.isEmpty();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String formatPrice(double value) {
        return round(value,2) + euroSymbol;
    }
}
