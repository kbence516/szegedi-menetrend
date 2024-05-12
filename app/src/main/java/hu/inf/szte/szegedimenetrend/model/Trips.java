package hu.inf.szte.szegedimenetrend.model;

public class Trips {
    private Integer direction_id;
    private Long route_id;
    private String service_id;
    private Integer shape_id;
    private String trip_headsign;
    private String trip_id;
    private Integer wheelchair_accessible;

    public Trips() {
    }

    public Integer getDirection_id() {
        return direction_id;
    }

    public Long getRoute_id() {
        return route_id;
    }

    public String getService_id() {
        return service_id;
    }

    public Integer getShape_id() {
        return shape_id;
    }

    public String getTrip_headsign() {
        return trip_headsign;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public Integer getWheelchair_accessible() {
        return wheelchair_accessible;
    }

    public void setDirection_id(Integer direction_id) {
        this.direction_id = direction_id;
    }

    public void setRoute_id(Long route_id) {
        this.route_id = route_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public void setShape_id(Integer shape_id) {
        this.shape_id = shape_id;
    }

    public void setTrip_headsign(String trip_headsign) {
        this.trip_headsign = trip_headsign;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public void setWheelchair_accessible(Integer wheelchair_accessible) {
        this.wheelchair_accessible = wheelchair_accessible;
    }
}
