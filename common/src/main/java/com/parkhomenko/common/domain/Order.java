package com.parkhomenko.common.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dmytro on 11.08.16.
 */
public class Order implements Serializable {
    private Long id;
    private String code;
    private Set<OrderProductItem> orderProductItems = new HashSet<>();

    public Order() {
    }

    public Set<OrderProductItem> getOrderProductItems() {
        return orderProductItems;
    }

    public void setOrderProductItems(Set<OrderProductItem> orderProductItems) {
        this.orderProductItems = orderProductItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}