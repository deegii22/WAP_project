package com.model;

import java.io.Serializable;

public class EventMember implements Serializable {
    private long eventID;
    private int userId;

    public long getEventID() {
        return eventID;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
