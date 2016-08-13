package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.Client;
import com.parkhomenko.common.domain.Order;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dmytro on 11.08.16.
 */
public abstract class AbstractDiscount implements Serializable {
    private Long id;
    private String code;
    private String description;
    private Date created;
    private Date start;
    private Date end;

    private AbstractDiscount nextDiscount;

    protected DiscountFetcher fetcher;

    public AbstractDiscount() {
    }

    public void setFetcher(DiscountFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public void setNextDiscount(AbstractDiscount nextDiscount) {
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDiscount that = (AbstractDiscount) o;

        return code != null ? code.equals(that.code) : that.code == null;

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}
