package me.jelco.theaterapp.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import me.jelco.theaterapp.tools.FormattingTools;

import java.time.LocalDateTime;

public class Showing {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Showing(String title, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public StringProperty startProperty() {
        return new SimpleStringProperty(FormattingTools.formatDateTime(startTime));
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public StringProperty endProperty() {
        return new SimpleStringProperty(FormattingTools.formatDateTime(endTime));
    }
}
