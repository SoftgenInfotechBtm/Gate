package com.softgen.gate.model;

import java.util.Date;

/**
 * Created by 9Jeevan on 18-10-2016.
 */
public class PlacesMaster {
    private String user_id,placesVisited;
    private Date createdAt;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPlacesVisited() {
        return placesVisited;
    }

    public void setPlacesVisited(String placesVisited) {
        this.placesVisited = placesVisited;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
