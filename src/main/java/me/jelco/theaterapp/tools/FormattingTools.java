package me.jelco.theaterapp.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormattingTools {
    public static String formatDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return time.format(formatter);
    }
    public static String formatTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return time.format(formatter);
    }
}
