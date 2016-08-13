package com.parkhomenko.common.domain;

import com.parkhomenko.common.domain.special_types.OrderStatus;
import com.parkhomenko.common.domain.util.MonetaryAmount;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dmytro on 11.08.16.
 */
public class Order implements Serializable {
    private Long id;
    private String code;
    private Date created;
    private Date deliveryDate;
    private Set<OrderProduct> orderProducts = new HashSet<>();
    private Warehouse warehouse;
    private Client client;
    private Address destination;
    private String notes;
    private OrderStatus status;
    private boolean isUrgent;
    private MonetaryAmount trafficCoast;
    private MonetaryAmount productsCoast;
    private MonetaryAmount totalCoast;
    private Driver driver;
    private Set<OrderHistory> history = new HashSet<>();

    public Order() {
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
        this.destination = destination;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public MonetaryAmount getTrafficCoast() {
        return trafficCoast;
    }

    public void setTrafficCoast(MonetaryAmount trafficCoast) {
        this.trafficCoast = trafficCoast;
    }

    public MonetaryAmount getProductsCoast() {
        return productsCoast;
    }

    public void setProductsCoast(MonetaryAmount productsCoast) {
        this.productsCoast = productsCoast;
    }

    public MonetaryAmount getTotalCoast() {
        return totalCoast;
    }

    public void setTotalCoast(MonetaryAmount totalCoast) {
        this.totalCoast = totalCoast;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Set<OrderHistory> getHistory() {
        return history;
    }

    public void setHistory(Set<OrderHistory> history) {
        this.history = history;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return code != null ? code.equals(order.code) : order.code == null;

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}