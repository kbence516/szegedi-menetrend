package hu.inf.szte.szegedimenetrend.model;


import java.util.Calendar;
import java.util.Date;

public class StopTimes implements Comparable<StopTimes> {
    private Date arrival_time;
    private Date departure_time;
    private Integer drop_off_type;
    private Integer pickup_type;
    private Integer shape_dist_traveled;
    private Long stop_id;
    private Integer stop_sequence;
    private String trip_id;

    public StopTimes() {
    }

    public Date getArrival_time() {
        return arrival_time;
    }

    public Date getDeparture_time() {
        return departure_time;
    }

    public Long getStop_id() {
        return stop_id;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public Integer getStop_sequence() {
        return stop_sequence;
    }

    public Integer getPickup_type() {
        return pickup_type;
    }

    public Integer getDrop_off_type() {
        return drop_off_type;
    }

    public Integer getShape_dist_traveled() {
        return shape_dist_traveled;
    }

    public void setArrival_time(Date arrival_time) {
        this.arrival_time = arrival_time;
    }

    public void setDeparture_time(Date departure_time) {
        this.departure_time = departure_time;
    }

    public void setDrop_off_type(Integer drop_off_type) {
        this.drop_off_type = drop_off_type;
    }

    public void setPickup_type(Integer pickup_type) {
        this.pickup_type = pickup_type;
    }

    public void setShape_dist_traveled(Integer shape_dist_traveled) {
        this.shape_dist_traveled = shape_dist_traveled;
    }

    public void setStop_id(Long stop_id) {
        this.stop_id = stop_id;
    }

    public void setStop_sequence(Integer stop_sequence) {
        this.stop_sequence = stop_sequence;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    @Override
    public int compareTo(StopTimes other) {
        Calendar thisCalendar = Calendar.getInstance();
        thisCalendar.setTime(this.arrival_time);
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTime(other.arrival_time);
        return (thisCalendar.get(Calendar.HOUR_OF_DAY) * 60 + thisCalendar.get(Calendar.MINUTE))
                - (otherCalendar.get(Calendar.HOUR_OF_DAY) * 60 + otherCalendar.get(Calendar.MINUTE));
    }
}
