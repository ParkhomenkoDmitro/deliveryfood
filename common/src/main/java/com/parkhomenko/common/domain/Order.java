package com.parkhomenko.common.domain;

import com.parkhomenko.common.domain.discount.Discount;
import com.parkhomenko.common.domain.discount.DiscountSupplier;
import com.parkhomenko.common.domain.discount.DiscountOne;
import com.parkhomenko.common.domain.discount.DiscountTwo;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmount;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmountFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmytro Parkhomenko
 * Created on 11.08.16.
 */

public class Order implements Serializable {
    public enum Status {
        WAITING, ASSEMBLING, RFP, DELIVERING, DELIVERED, CANCELED, TROUBLE;
    }

    private Long id;
    private String code;
    private LocalDateTime createdDateTime;
    private LocalDateTime deliveryDateTime;
    private Set<OrderProduct> orderProducts = new HashSet<>();
    private Warehouse warehouse;
    private Client client;
    private Address clientAddress;
    private String notes;
    private Status status;
    private boolean isUrgent;
    private MonetaryAmount trafficPrice;
    private MonetaryAmount productsPrice;
    private MonetaryAmount totalPrice;
    private Driver driver;
    private Set<OrderHistory> history = new HashSet<>();

    public Order() {
    }

    public void calculatePrice(DiscountSupplier fetcher) {
        productsPrice = calculateOrderProductsPrice(fetcher);
        trafficPrice = calculateTrafficPrice(clientAddress, warehouse.getAddress());
        totalPrice = productsPrice.add(trafficPrice);
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(LocalDateTime deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
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

    public Address getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(Address clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public MonetaryAmount getTrafficPrice() {
        return trafficPrice;
    }

    public void setTrafficPrice(MonetaryAmount trafficPrice) {
        this.trafficPrice = trafficPrice;
    }

    public MonetaryAmount getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(MonetaryAmount productsPrice) {
        this.productsPrice = productsPrice;
    }

    public MonetaryAmount getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(MonetaryAmount totalPrice) {
        this.totalPrice = totalPrice;
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

    private void calculateProductsPriceWithDiscounts(DiscountSupplier fetcher) {
        Discount discountOne = new DiscountOne();
        Discount discountTwo = new DiscountTwo();
        discountOne.setNextDiscount(discountTwo);
        discountOne.setFetcher(fetcher);
        discountTwo.setFetcher(fetcher);
        discountOne.calculateDiscount(this);
        getOrderProducts().forEach(OrderProduct::calculatePrice);
    }

    private MonetaryAmount calculateOrderProductsPrice(DiscountSupplier fetcher) {
        calculateProductsPriceWithDiscounts(fetcher);
        MonetaryAmount price = MonetaryAmountFactory.getUSDZeroMonetaryAmount();
        for(OrderProduct orderProduct : orderProducts) {
            price = price.add(orderProduct.getPrice());
        }
        return price;
    }

    private static MonetaryAmount calculateTrafficPrice(Address start, Address finish) {
        //TODO traffic logic here
        return MonetaryAmountFactory.getUSDZeroMonetaryAmount();
    }
}