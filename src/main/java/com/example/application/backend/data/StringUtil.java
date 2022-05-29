package com.example.application.backend.data;
public class StringUtil {

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
}
