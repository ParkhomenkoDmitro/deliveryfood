package com.parkhomenko.common.domain;

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
    private Set admins = new HashSet();
    private Set<WarehouseProduct> products = new HashSet<>();

    public Warehouse() {
    }

    public void setAdmins(Set admins) {
        this.admins = admins;
    }

    public Set<WarehouseProduct> getProducts() {
        return products;
    }

    public void setProducts(Set<WarehouseProduct> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set getAdmins() {
        return admins;
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
