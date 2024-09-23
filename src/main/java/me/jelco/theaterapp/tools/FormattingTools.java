package me.jelco.theaterapp.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormattingTools {
    public static String formatTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formatted = time.format(formatter);

        return formatted;
    }
}
