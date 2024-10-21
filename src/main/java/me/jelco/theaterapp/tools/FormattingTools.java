package me.jelco.theaterapp.tools;

import me.jelco.theaterapp.models.*;

import java.time.*;
import java.time.format.*;

public class FormattingTools {
    public static String formatDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        return time.format(formatter);
    }

    public static String formatTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return time.format(formatter);
    }

    public static String ShowingToCSV(Showing showing) {
        String base = "";
        base += showing.getStartTime();
        base += ",";
        base += showing.getEndTime();
        base += ",";
        base += showing.getTitle();
        base += ",";
        base += showing.getRoom().getSeats() - showing.calculateOccupiedSeats();

        return base;
    }
}
