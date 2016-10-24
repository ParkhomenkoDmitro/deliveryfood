package com.parkhomenko.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.parkhomenko.common.domain.util.View;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmytro Parkhomenko
 * Created on 22.07.16.
 */

public class Warehouse implements Serializable {

    private Long id;
    private Address address;
    private Set<Admin> admins = new HashSet<>();
    private Set<WarehouseProduct> products = new HashSet<>();

    public Warehouse() {
    }

    @JsonView(View.WarehouseDetails.class)
    public Set<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
    }

    @JsonView(View.WarehouseDetails.class)
    public Set<WarehouseProduct> getProducts() {
        return products;
    }

    public void setProducts(Set<WarehouseProduct> products) {
        this.products = products;
    }

    @JsonView(View.Summary.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(View.Summary.class)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Warehouse warehouse = (Warehouse) o;

        return address != null ? address.equals(warehouse.address) : warehouse.address == null;

    }

    @Override
    public int hashCode() {
        return address != null ? address.hashCode() : 0;
    }
}
