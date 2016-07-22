package com.parkhomenko.common.domain;

import java.io.Serializable;

/**
 * Created by dmytro on 22.07.16.
 */
public class Driver extends User implements Serializable {
    private Car car;
    private String license;
    private City city;

    public Driver() {
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
