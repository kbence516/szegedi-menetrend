package hu.inf.szte.szegedimenetrend.model;

public class Shapes {
    private Integer shape_dist_traveled;
    private Long shape_id;
    private Double shape_pt_lat;
    private Double shape_pt_lon;
    private Integer shape_pt_sequence;

    public Shapes() {
    }

    public Long getShape_id() {
        return shape_id;
    }

    public Double getShape_pt_lat() {
        return shape_pt_lat;
    }

    public Double getShape_pt_lon() {
        return shape_pt_lon;
    }

    public Integer getShape_pt_sequence() {
        return shape_pt_sequence;
    }

    public Integer getShape_dist_traveled() {
        return shape_dist_traveled;
    }

    public void setShape_dist_traveled(Integer shape_dist_traveled) {
        this.shape_dist_traveled = shape_dist_traveled;
    }

    public void setShape_id(Long shape_id) {
        this.shape_id = shape_id;
    }

    public void setShape_pt_lat(Double shape_pt_lat) {
        this.shape_pt_lat = shape_pt_lat;
    }

    public void setShape_pt_lon(Double shape_pt_lon) {
        this.shape_pt_lon = shape_pt_lon;
    }

    public void setShape_pt_sequence(Integer shape_pt_sequence) {
        this.shape_pt_sequence = shape_pt_sequence;
    }
}
