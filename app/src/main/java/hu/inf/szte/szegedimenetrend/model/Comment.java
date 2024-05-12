package hu.inf.szte.szegedimenetrend.model;

import java.util.Date;

public class Comment {
    private String firebase_id;
    private String route_short_name;
    private String author_email;
    private String author_name;
    private String comment;
    private Date last_modified;

    public Comment() {
    }

    public String _getFirebase_id() {
        return firebase_id;
    }

    public void _setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRoute_short_name() {
        return route_short_name;
    }

    public void setRoute_short_name(String route_short_name) {
        this.route_short_name = route_short_name;
    }

    public Date getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }
}
