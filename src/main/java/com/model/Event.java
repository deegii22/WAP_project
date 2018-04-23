package com.model;

import java.util.Date;

public class Event {
    long eventID;
    public long getEventID() {
        return eventID;
    }
    String name;
    public String getEventName() {
        return name;
    }
    int ownerID;
    public int getOwnerID() {
        return ownerID;
    }
    String route_img;
    public String getRoute_img() {
        return route_img;
    }
    Date start_date;
    public Date getStart_date() {
        return start_date;
    }
    Date end_date;
    public Date getEnd_date() {
        return end_date;
    }
    int emergency_flag;
    public int getEmergency_flag() {
        return emergency_flag;
    }
    String emergency_info;
    public String getEmergency_info() {
        return emergency_info;
    }
    Date created;
    public Date getCreated() {
        return created;
    }
    int status;
    public int getStatus() {
        return status;
    }

    public Event(long eventID, String eventName, int ownerID, String Route, Date Start, Date End) {
        this.eventID = eventID;
        this.name = eventName;
        this.ownerID = ownerID;
        this.route_img = Route;
        this.start_date = Start;
        this.end_date = End;

        this.status = 0;
    }
}
