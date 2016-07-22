package com.parkhomenko.common.domain;

import java.io.Serializable;

/**
 * Created by dmytro on 22.07.16.
 */
public class Address implements Serializable {
    private City city;
    private Geolocation geolocation;
    private String street;
    private String house;
    private Integer entrance;
    private Integer flat;

    public Address() {
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public Integer getEntrance() {
        return entrance;
    }

    public void setEntrance(Integer entrance) {
        this.entrance = entrance;
    }

    public Integer getFlat() {
        return flat;
    }

    public void setFlat(Integer flat) {
        this.flat = flat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (house != null ? !house.equals(address.house) : address.house != null) return false;
        if (entrance != null ? !entrance.equals(address.entrance) : address.entrance != null) return false;
        return flat != null ? flat.equals(address.flat) : address.flat == null;

    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + (entrance != null ? entrance.hashCode() : 0);
        result = 31 * result + (flat != null ? flat.hashCode() : 0);
        return result;
    }
}
