package com.parkhomenko.common.domain.special_types;

/**
 * Created by dmytro on 13.08.16.
 */
public enum OrderStatus {
    WAITING(0),
    ASSEMBLING(1),
    RFP(2),
    DELIVERING(3),
    DELIVERED(4),
    CANCELED(5),
    TROUBLE(6);

    private int id;

    OrderStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
