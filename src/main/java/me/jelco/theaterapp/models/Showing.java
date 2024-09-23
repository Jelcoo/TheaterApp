package me.jelco.theaterapp.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import me.jelco.theaterapp.tools.FormattingTools;

import java.time.LocalDateTime;

public class Showing {
    String title;
    LocalDateTime startTime;
    LocalDateTime endTime;

    public Showing(String title, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public StringProperty startProperty() {
        return new SimpleStringProperty(FormattingTools.formatTime(startTime));
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public StringProperty endProperty() {
        return new SimpleStringProperty(FormattingTools.formatTime(endTime));
    }
}
