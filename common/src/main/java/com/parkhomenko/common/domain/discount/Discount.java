package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.Client;
import com.parkhomenko.common.domain.Order;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Dmytro Parkhomenko
 * Created on 11.08.16.
 */

public abstract class Discount implements Serializable {
    private Long id;
    private String code;
    private String description;
    private LocalDateTime createdDateTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Discount nextDiscount;

    protected DiscountFetcher fetcher;

    public Discount() {
    }

    public void setFetcher(DiscountFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public void setNextDiscount(Discount nextDiscount) {
        this.nextDiscount = nextDiscount;
    }

    public void calculateDiscount(Client client, Order order) {
        calculate(client, order);
        if (nextDiscount != null) {
            nextDiscount.calculateDiscount(client, order);
        }
    }

    protected abstract void calculate(Client client, Order order);

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discount that = (Discount) o;

        return code != null ? code.equals(that.code) : that.code == null;

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}
