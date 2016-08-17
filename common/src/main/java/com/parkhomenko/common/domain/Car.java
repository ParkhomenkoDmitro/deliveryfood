package com.parkhomenko.common.domain;

import java.io.Serializable;

/**
 * @author Dmytro Parkhomenko
 * Created on 22.07.16.
 */

public class Car implements Serializable {
    private String number;
    private String brand;

    public Car() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return number != null ? number.equals(car.number) : car.number == null;

    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }
}
