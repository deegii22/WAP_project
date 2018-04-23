package com.model;

import java.util.Date;

public class Event {
    String eventName;
    int ownerID;
    String route;
    Date start;
    Date end;
    int status;

    public String getEventName() {
        return eventName;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public String getRoute() {
        return route;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getStatus() {
        return status;
    }

    public Event(String eventName, int ownerID, String Route, Date Start, Date End) {
        this.eventName = eventName;
        this.ownerID = ownerID;
        this.route = Route;
        this.start = Start;
        this.end = End;
        this.status = 0;
    }
}
