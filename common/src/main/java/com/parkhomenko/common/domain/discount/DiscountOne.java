package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.*;
import com.parkhomenko.common.domain.special_types.DiscountType;
import com.parkhomenko.common.domain.special_types.MonetaryAmount;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that represent DiscountOne discount type logic and will be applied to all products in order that:
 * supported discount of this type and it is the first discount that will be applied to this product.
 * DiscountOne discount type logic: product price replaced by predefined discount price of this product.
 *
 * @author  Dmytro Parkhomenko
 * Created on 12.08.16.
 */

public class DiscountOne extends Discount {
    private MonetaryAmount price;
    private Product product;

    public DiscountOne() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

    @Override
    protected void calculate(Order order) {
        Set<OrderProduct> noAppliedDiscountsProducts = getNoAppliedDiscountsProducts(order.getOrderProducts());
        Map<OrderProduct, DiscountOne> map = findAppropriateOrderProducts(noAppliedDiscountsProducts, order.getCreatedDateTime());
        map.keySet().forEach(orderProduct -> applyDiscount(orderProduct, map.get(orderProduct)));
    }

    private void applyDiscount(OrderProduct orderProduct, DiscountOne discount) {
        AppliedDiscount appliedDiscount = new AppliedDiscount();
        Product product = orderProduct.getProduct();
        appliedDiscount.setCount(orderProduct.getCount());
        appliedDiscount.setDiscountCode(discount.getCode());
        appliedDiscount.setType(DiscountType.TYPE_ONE);
        appliedDiscount.setPriceForOne(discount.getPrice());
        appliedDiscount.setTotalPrice(product.calcPrice(discount.getPrice(), orderProduct.getCount()));
        appliedDiscount.setOrderProduct(orderProduct);
        orderProduct.getAppliedDiscounts().add(appliedDiscount);
    }

    private Set<OrderProduct> getNoAppliedDiscountsProducts(Set<OrderProduct> orderProducts) {
        return orderProducts.stream().filter(orderProduct -> orderProduct.getAppliedDiscounts().isEmpty()).collect(Collectors.toSet());
    }

    private Map<OrderProduct, DiscountOne> findAppropriateOrderProducts(Set<OrderProduct> products, LocalDateTime orderCreationDateTime) {
        Map<OrderProduct, DiscountOne> discounts = new HashMap<>();
        for(OrderProduct orderProduct : products) {
            Discount discount = fetcher.fetch(orderProduct.getProduct(), orderCreationDateTime);
            if(discount.isValid(orderCreationDateTime)) {
                discounts.put(orderProduct, (DiscountOne) discount);
            }
        }
        return discounts;
    }
}