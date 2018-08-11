package ru.geekdroidstudio.ridr.model.repository.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.geekdroidstudio.ridr.model.repository.entity.directionapiresponse.GeocodedWayPoint;
import ru.geekdroidstudio.ridr.model.repository.entity.directionapiresponse.Route;


public class RouteDrivingModelResponse {
    /* точки по которым осуществлялся поиск*/
    @SerializedName("geocoded_waypoints")
    @Expose
    private List<GeocodedWayPoint> geocodedWayPoints;

    /* в ответе нам приходит массив маршрутов Routes, который содержит массив отрезков Legs,
     * состоящий из шагов Steps, составляющих отрезок маршрута, и информации об отрезке. В ранних
     * примерах маршруты строились на основе информации о каждом шаге отрезка, однако уже в
     * объекте Route содержится объект Overview_polyline — это объект с массивом закодированных
     * элементов points, которые представляют приблизительный (сглаженный) путь результирующего
     * маршрута. В большинстве случаев этого сглаженного маршрута будет достаточно.*/
    @SerializedName("routes")
    @Expose
    private List<Route> routes = null;

    @SerializedName("status")
    @Expose
    private String status;

    public List<GeocodedWayPoint> getGeocodedWayPoints() {
        return geocodedWayPoints;
    }

    public void setGeocodedWayPoints(List<GeocodedWayPoint> geocodedWayPoints) {
        this.geocodedWayPoints = geocodedWayPoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
