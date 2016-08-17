package com.parkhomenko.common.domain;

import com.parkhomenko.common.domain.special_types.Measure;

/**
 * @author Dmytro Parkhomenko
 * Created on 09.08.16.
 */

public class ProductParameter {
    private Double value;
    private Measure measure;

    public ProductParameter() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }
}
