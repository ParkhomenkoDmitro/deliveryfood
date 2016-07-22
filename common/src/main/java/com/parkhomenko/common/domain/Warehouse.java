package com.parkhomenko.common.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by dmytro on 22.07.16.
 */
public class Warehouse implements Serializable {
    private Long id;
    private Address address;
    private Set<Admin> admins;

    public Warehouse() {
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

    public Set<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
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
