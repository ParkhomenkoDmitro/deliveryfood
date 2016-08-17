package com.parkhomenko.common.domain;

import java.io.Serializable;

/**
 * @author Dmytro Parkhomenko
 * Created on 22.07.16.
 */

public class Geolocation implements Serializable {
    private Double lat;
    private Double lng;

    public Geolocation() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Geolocation that = (Geolocation) o;

        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;
        return lng != null ? lng.equals(that.lng) : that.lng == null;

    }

    @Override
    public int hashCode() {
        int result = lat != null ? lat.hashCode() : 0;
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        return result;
    }
}
