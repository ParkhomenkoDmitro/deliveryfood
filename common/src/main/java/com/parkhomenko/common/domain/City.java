package com.parkhomenko.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.parkhomenko.common.domain.util.View;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Dmytro Parkhomenko
 * Created on 22.07.16.
 */

public class City implements Serializable {

    private Long id;
    private String name;
    private Geolocation geolocation;
    private Set<Warehouse> warehouses;

    public City() {
    }

    @JsonView(View.Summary.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(View.Summary.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonView(View.CityDetails.class)
    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    @JsonView(View.CityDetails.class)
    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return name != null ? name.equals(city.name) : city.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
