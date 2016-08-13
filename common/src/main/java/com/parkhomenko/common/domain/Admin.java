package com.parkhomenko.common.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dmytro on 22.07.16.
 */
public class Admin extends User implements Serializable {
    private Set warehouses = new HashSet();

    public Admin() {
    }

    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
}
