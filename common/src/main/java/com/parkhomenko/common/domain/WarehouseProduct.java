package com.parkhomenko.common.domain;

import java.io.Serializable;

/**
 * Created by dmytro on 13.08.16.
 */
public class WarehouseProduct implements Serializable {
    private Warehouse warehouse;
    private Product product;
    private Long count;

    public WarehouseProduct() {
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarehouseProduct that = (WarehouseProduct) o;

        if (warehouse != null ? !warehouse.equals(that.warehouse) : that.warehouse != null) return false;
        return product != null ? product.equals(that.product) : that.product == null;

    }

    @Override
    public int hashCode() {
        int result = warehouse != null ? warehouse.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}
