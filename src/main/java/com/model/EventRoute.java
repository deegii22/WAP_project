package com.model;

import java.io.Serializable;

public class EventRoute implements Serializable {
    private long eventID;
    private String startPosition;
    private String endPosition;
    private int priority;
    private int status;
    private int duration;

    public long getEventID() {
        return eventID;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public int getPriority() {
        return priority;
    }

    public int getStatus() {
        return status;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

}
