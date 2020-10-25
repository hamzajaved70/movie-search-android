package com.example.assg3;

public class Cinema {
    private Integer cinemaid;
    private String cinemaname;
    private String location;

    public Cinema() {
    }

    public Cinema(Integer cinemaid) {
        this.cinemaid = cinemaid;
    }

    public Integer getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Integer cinemaid) {
        this.cinemaid = cinemaid;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
