package ru.geekdroidstudio.ridr.model.repository.entity.directionapiresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Leg {
    @SerializedName("distance")
    @Expose
    private ApiObject distance;
    @SerializedName("duration")
    @Expose
    private ApiObject duration;
    @SerializedName("end_address")
    @Expose
    private String endAddress;
    @SerializedName("end_location")
    @Expose
    private Point endLocation;
    @SerializedName("start_address")
    @Expose
    private String startAddress;
    @SerializedName("start_location")
    @Expose
    private Point startLocation;
    @SerializedName("steps")
    @Expose
    private List<Step> steps;
    @SerializedName("traffic_speed_entry")
    @Expose//not define object
    private List<Object> trafficSpeedEntry;
    @SerializedName("via_waypoint")
    @Expose
    private List<GeocodedWayPoint> viaWaypoint;

    public ApiObject getDistance() {
        return distance;
    }

    public void setDistance(ApiObject distance) {
        this.distance = distance;
    }

    public ApiObject getDuration() {
        return duration;
    }

    public void setDuration(ApiObject duration) {
        this.duration = duration;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public Point getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Point endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public Point getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Point startLocation) {
        this.startLocation = startLocation;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Object> getTrafficSpeedEntry() {
        return trafficSpeedEntry;
    }

    public void setTrafficSpeedEntry(List<Object> trafficSpeedEntry) {
        this.trafficSpeedEntry = trafficSpeedEntry;
    }

    public List<GeocodedWayPoint> getViaWaypoint() {
        return viaWaypoint;
    }

    public void setViaWaypoint(List<GeocodedWayPoint> viaWaypoint) {
        this.viaWaypoint = viaWaypoint;
    }

}
