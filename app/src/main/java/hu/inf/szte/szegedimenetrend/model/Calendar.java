package hu.inf.szte.szegedimenetrend.model;


import java.util.Date;

public class Calendar {
    private Date end_date;
    private Integer friday;
    private Integer monday;
    private Integer saturday;
    private String service_id;
    private Date start_date;
    private Integer sunday;
    private Integer thursday;
    private Integer tuesday;
    private Integer wednesday;

    public Calendar() {
    }

    public String getService_id() {
        return service_id;
    }

    public Integer getMonday() {
        return monday;
    }

    public Integer getTuesday() {
        return tuesday;
    }

    public Integer getWednesday() {
        return wednesday;
    }

    public Integer getThursday() {
        return thursday;
    }

    public Integer getFriday() {
        return friday;
    }

    public Integer getSaturday() {
        return saturday;
    }

    public Integer getSunday() {
        return sunday;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setFriday(Integer friday) {
        this.friday = friday;
    }

    public void setMonday(Integer monday) {
        this.monday = monday;
    }

    public void setSaturday(Integer saturday) {
        this.saturday = saturday;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setSunday(Integer sunday) {
        this.sunday = sunday;
    }

    public void setThursday(Integer thursday) {
        this.thursday = thursday;
    }

    public void setTuesday(Integer tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(Integer wednesday) {
        this.wednesday = wednesday;
    }
}
