package hu.inf.szte.szegedimenetrend.model;

import java.sql.Timestamp;

public class CalendarDates {
    private Long service_id;
    private Timestamp date;
    private Integer exception_type;

    public CalendarDates() {
    }

    public Long getService_id() {
        return service_id;
    }

    public Timestamp getDate() {
        return date;
    }

    public Integer getException_type() {
        return exception_type;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setException_type(Integer exception_type) {
        this.exception_type = exception_type;
    }
}
