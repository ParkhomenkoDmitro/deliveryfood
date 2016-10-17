package com.parkhomenko.common.domain;

import com.parkhomenko.common.domain.discount.AppliedDiscount;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmount;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmountFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Parkhomenko
 * Created on 11.08.16.
 */

public class OrderProduct implements Serializable {
    private Order order;
    private ProductVersion product;
    private Integer count;
    private MonetaryAmount price;
    private List<AppliedDiscount> appliedDiscounts = new ArrayList<>();

    public OrderProduct() {
    }

    public void calculatePrice() {
        if (appliedDiscounts.isEmpty()) {
            price = product.calcPrice(count);
        } else {
            price = MonetaryAmountFactory.ZERO;
            appliedDiscounts.forEach(appliedDiscount -> price = price.add(appliedDiscount.getTotalPrice()));
        }
    }

    public List<AppliedDiscount> getAppliedDiscounts() {
        return appliedDiscounts;
    }

    public void setAppliedDiscounts(List<AppliedDiscount> appliedDiscounts) {
        this.appliedDiscounts = appliedDiscounts;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductVersion getProduct() {
        return product;
    }

    public void setProduct(ProductVersion product) {
        this.product = product;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProduct that = (OrderProduct) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        return product != null ? product.equals(that.product) : that.product == null;

    }

    @Override
    public int hashCode() {
        int result = order != null ? order.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}
