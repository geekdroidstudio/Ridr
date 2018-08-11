package ru.geekdroidstudio.ridr.model.repository.entity.directionapiresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bounds {
    @SerializedName("northeast")
    @Expose
    private Point northeast;
    @SerializedName("southwest")
    @Expose
    private Point southwest;

    public Point getNortheast() {
        return northeast;
    }

    public void setNortheast(Point northeast) {
        this.northeast = northeast;
    }

    public Point getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Point southwest) {
        this.southwest = southwest;
    }
}
