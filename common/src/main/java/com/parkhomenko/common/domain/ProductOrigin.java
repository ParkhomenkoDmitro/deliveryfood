package com.parkhomenko.common.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmytro Parkhomenko
 *         Created on 30.08.16.
 */

public class ProductOrigin extends Product {
    private Set<WarehouseProduct> warehouses = new HashSet<>();

    public ProductOrigin() {
    }

    public Set<WarehouseProduct> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<WarehouseProduct> warehouses) {
        this.warehouses = warehouses;
    }
}
