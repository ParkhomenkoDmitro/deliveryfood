package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.special_types.MonetaryAmount;
import com.parkhomenko.common.domain.OrderProduct;
import com.parkhomenko.common.domain.special_types.DiscountType;

import java.io.Serializable;

/**
 * @author Dmytro Parkhomenko
 * Created on 12.08.16.
 */

public class AppliedDiscount implements Serializable {
    private Long id;
    private DiscountType type;
    private String discountCode;
    private Integer count;
    private MonetaryAmount priceForOne;
    private MonetaryAmount totalPrice;
    private OrderProduct orderProduct;

    public AppliedDiscount() {
    }

    public MonetaryAmount getPriceForOne() {
        return priceForOne;
    }

    public void setPriceForOne(MonetaryAmount priceForOne) {
        this.priceForOne = priceForOne;
    }

    public MonetaryAmount getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(MonetaryAmount totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiscountType getType() {
        return type;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppliedDiscount that = (AppliedDiscount) o;

        if (!getDiscountCode().equals(that.getDiscountCode())) return false;
        return getOrderProduct().equals(that.getOrderProduct());

    }

    @Override
    public int hashCode() {
        int result = getDiscountCode().hashCode();
        result = 31 * result + getOrderProduct().hashCode();
        return result;
    }
}
