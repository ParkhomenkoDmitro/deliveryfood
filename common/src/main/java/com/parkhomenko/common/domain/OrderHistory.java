package com.parkhomenko.common.domain;

import com.parkhomenko.common.domain.Order.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Dmytro Parkhomenko
 * Created on 13.08.16.
 */

public class OrderHistory implements Serializable {
    private Long id;
    private String note;
    private LocalDateTime createdDateTime;
    private Status status;
    private User user;
    private Order order;

    public OrderHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderHistory that = (OrderHistory) o;

        if (createdDateTime != null ? !createdDateTime.equals(that.createdDateTime) : that.createdDateTime != null) return false;
        if (status != that.status) return false;
        return order != null ? order.equals(that.order) : that.order == null;

    }

    @Override
    public int hashCode() {
        int result = createdDateTime != null ? createdDateTime.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }
}
