package com.parkhomenko.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.parkhomenko.common.domain.util.View;

import java.io.Serializable;

/**
 * @author Dmytro Parkhomenko
 * Created on 22.07.16.
 */

public class Role implements Serializable {

    private Long id;
    private String name;

    public Role() {
    }

    @JsonView(View.Summary.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(View.Summary.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name != null ? name.equals(role.name) : role.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
