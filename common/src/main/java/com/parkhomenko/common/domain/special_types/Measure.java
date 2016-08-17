package com.parkhomenko.common.domain.special_types;

/**
 * @author Dmytro Parkhomenko
 * Created on 10.08.16.
 */

public enum Measure {
    G(0),
    KG(1),
    TON(2),
    L(3),
    PS(4),
    NONE(5);

    private int id;

    Measure(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
