package com.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Event implements Serializable {
    private long eventID;
    private String name;
    private int status;
    private String route;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String img;
    private int ownerId;
    private int eFlag;
    private String eInfo;
    private LocalDateTime created;
    private ArrayList<EventRoute> routes = null;

    public long getEventID() {
        return eventID;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int geteFlag() {
        return eFlag;
    }

    public void seteFlag(int eFlag) {
        this.eFlag = eFlag;
    }

    public String geteInfo() {
        return eInfo;
    }

    public void seteInfo(String eInfo) {
        this.eInfo = eInfo;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void addRoute(EventRoute route){
        if(routes == null)
            routes = new ArrayList<EventRoute>();
        routes.add(route);
    }
    public ArrayList<EventRoute> getRoutes(){
        return routes;
    }
}
