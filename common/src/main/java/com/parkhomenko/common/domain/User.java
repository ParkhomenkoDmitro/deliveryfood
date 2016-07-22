package com.parkhomenko.common.domain;

import java.io.Serializable;

/**
 * Created by dmytro on 19.07.16.
 */
public class User implements Serializable {
    private Long id;
    private String name;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
