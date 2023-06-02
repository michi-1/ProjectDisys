package org.example;

public class Station {
    private int id;
    private String db_url;
    private float lat;
    private float lng;

    public Station(int id, String db_url, float lat, float lng) {
        this.id = id;
        this.db_url = db_url;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db) {
        this.db_url = db;
    }

    public float Getlat() {
        return lat;
    }

    public void Setlat(float lat) {
        this.lat = lat;
    }

    public float Getlng() {
        return lng;
    }

    public void Setlng(float lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Station {" +
                "id=" + id +
                ", DB URL='" + db_url + '\'' +
                ", lat ='" + lat + '\'' +
                ", lng ='" + lng + '\''+
                '}';
    }
}