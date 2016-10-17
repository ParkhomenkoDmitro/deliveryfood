package com.parkhomenko.common.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Dmytro Parkhomenko
 * Created on 22.07.16.
 */

public class Admin extends User implements Serializable {
    private Set<Warehouse> warehouses = new HashSet<>();

    public Admin() {
    }

    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public void update(Admin updatedAdmin) {
        if(Objects.nonNull(updatedAdmin.getName())) {
            setName(updatedAdmin.getName());
        }

        if(Objects.nonNull(updatedAdmin.getLogin())) {
            setLogin(updatedAdmin.getLogin());
        }

        //TODO: add here others fields
    }
}
