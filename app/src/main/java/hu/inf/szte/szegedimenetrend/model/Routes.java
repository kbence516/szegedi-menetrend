package hu.inf.szte.szegedimenetrend.model;

import android.util.Log;

public class Routes implements Comparable<Routes> {
    private Long agency_id;
    private String route_color;
    private String route_desc;
    private Long route_id;
    private String route_long_name;
    private String route_short_name;
    private String route_text_color;
    private Integer route_type;
    private Double route_url;       // NaN az adatbázisban, amúgy Stringnek kéne lennie :D

    public Routes() {
    }

    public Long getRoute_id() {
        return route_id;
    }

    public Long getAgency_id() {
        return agency_id;
    }

    public String getRoute_short_name() {
        return route_short_name;
    }

    public String getRoute_long_name() {
        return route_long_name;
    }

    public String getRoute_desc() {
        return route_desc;
    }

    public Integer getRoute_type() {
        return route_type;
    }

    public Double getRoute_url() {
        return route_url;
    }

    public String getRoute_color() {
        return route_color;
    }

    public String getRoute_text_color() {
        return route_text_color;
    }

    public void setAgency_id(Long agency_id) {
        this.agency_id = agency_id;
    }

    public void setRoute_color(String route_color) {
        this.route_color = route_color;
    }

    public void setRoute_desc(String route_desc) {
        this.route_desc = route_desc;
    }

    public void setRoute_id(Long route_id) {
        this.route_id = route_id;
    }

    public void setRoute_long_name(String route_long_name) {
        this.route_long_name = route_long_name;
    }

    public void setRoute_short_name(String route_short_name) {
        this.route_short_name = route_short_name;
    }

    public void setRoute_text_color(String route_text_color) {
        this.route_text_color = route_text_color;
    }

    public void setRoute_type(Integer route_type) {
        this.route_type = route_type;
    }

    public void setRoute_url(Double route_url) {
        this.route_url = route_url;
    }

    @Override
    public int compareTo(Routes otherRoute) {
        String rsn = this.route_short_name;
        String orsn = otherRoute.route_short_name;

        if (!Character.isDigit(rsn.charAt(0)) || !Character.isDigit(orsn.charAt(0))) {
            return rsn.compareTo(orsn);
        }
        int rsx = 1;
        int orsx = 1;
        try {
            rsx = Integer.parseInt(rsn);
        } catch (NumberFormatException e) {
            for (int i = 1; i < rsn.length(); i++) {
                if (!Character.isDigit(rsn.charAt(i))) {
                    //legalább az első karakter szám
                    rsx = Integer.parseInt(rsn.substring(0, i));
                    break;
                }
            }
        }
        try {
            orsx = Integer.parseInt(orsn);
        } catch (NumberFormatException e) {
            for (int i = 1; i < orsn.length(); i++) {
                if (!Character.isDigit(orsn.charAt(i))) {
                    //legalább az első karakter szám
                    orsx = Integer.parseInt(orsn.substring(0, i));
                    break;
                }
            }
        }
        return rsx == orsx ? rsn.compareTo(orsn) : rsx - orsx;
    }
}
