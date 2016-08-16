package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.Client;
import com.parkhomenko.common.domain.Order;
import com.parkhomenko.common.domain.OrderProduct;
import com.parkhomenko.common.domain.Product;
import com.parkhomenko.common.domain.special_types.DiscountType;
import com.parkhomenko.common.domain.util.MonetaryAmount;
import com.parkhomenko.common.domain.util.MonetaryAmountFactory;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

/**
 * Created by dmytro on 12.08.16.
 */

/**
 * Class that represent DiscountTwo discount type logic and will be applied to all products in order that:
 * supported discount of this type and it is the first discount that will be applied to this product.
 *
 */

public class DiscountTwo extends AbstractDiscount {
    private Set<Product> products = new HashSet<>();

    public DiscountTwo() {
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    protected void calculate(Client client, Order order) {
        Map<OrderProduct, DiscountTwo> map = findAppropriateOrderProducts(getNoAppliedDiscountsProducts(order.getOrderProducts()));
        Set<DiscountTwo> discountsTypeTwo = new HashSet<>(map.values());
        discountsTypeTwo.forEach(discount -> doLogic(filterByDiscount(discount, map)));
    }

    private Map<OrderProduct, DiscountTwo> findAppropriateOrderProducts(Set<OrderProduct> products) {
        Map<OrderProduct, DiscountTwo> discounts = new HashMap<>();
        for(OrderProduct orderProduct : products) {
            DiscountTwo discount = fetcher.fetchDiscountTypeTwo(orderProduct.getProduct());
            if(discount != null) {
                discounts.put(orderProduct, discount);
            }
        }
        return discounts;
    }

    private Set<OrderProduct> getNoAppliedDiscountsProducts(Set<OrderProduct> orderProducts) {
        return orderProducts.stream().filter(orderProduct -> orderProduct.getAppliedDiscounts() == null).collect(Collectors.toSet());
    }

    private Map<OrderProduct, DiscountTwo> filterByDiscount(DiscountTwo discount, Map<OrderProduct, DiscountTwo> map) {
        Map<OrderProduct, DiscountTwo> result = new HashMap<>();
        for (OrderProduct orderProduct : map.keySet()) {
            DiscountTwo currentDiscount = map.get(orderProduct);
            if(discount.equals(currentDiscount)) {
                result.put(orderProduct, currentDiscount);
            }
        }
        return  result;
    }

    private void doLogic(Map<OrderProduct, DiscountTwo> appropriates) {
        final int EVERY_FREE_PRODUCT_INDEX = 3;

        List<OrderProduct> orderProducts = new ArrayList<>(appropriates.keySet());

        orderProducts.sort(comparing(OrderProduct::getProduct, comparing(Product::getPrice)));

        for(int allProductsQuantity = 0, freeProductsQuantity = 0, i = 0; i < orderProducts.size(); i++) {
            OrderProduct orderProduct = orderProducts.get(i);
            int productQuantity = orderProduct.getCount();

            allProductsQuantity += productQuantity;
            int freeProductQuantity = allProductsQuantity / EVERY_FREE_PRODUCT_INDEX - freeProductsQuantity;
            freeProductsQuantity += freeProductQuantity;

            if(freeProductQuantity > 0) {
                int payProductQuantity = productQuantity - freeProductQuantity;
                DiscountTwo discount = appropriates.get(orderProduct);
                Product product = orderProduct.getProduct();
                applyDiscount(discount, orderProduct, payProductQuantity, product.getPrice(), product.calcPrice(payProductQuantity));
                applyDiscount(discount, orderProduct, freeProductQuantity, MonetaryAmountFactory.ZERO, MonetaryAmountFactory.ZERO);
            }
        }
    }

    private void applyDiscount(DiscountTwo discount,
                               OrderProduct orderProduct,
                               int count,
                               MonetaryAmount priceForOne,
                               MonetaryAmount totalPrice) {
        AppliedDiscount appliedDiscount = new AppliedDiscount();
        appliedDiscount.setCount(count);
        appliedDiscount.setDiscountCode(discount.getCode());
        appliedDiscount.setType(DiscountType.TYPE_TWO);
        appliedDiscount.setPriceForOne(priceForOne);
        appliedDiscount.setTotalPrice(totalPrice);
        appliedDiscount.setOrderProduct(orderProduct);
        orderProduct.getAppliedDiscounts().add(appliedDiscount);
    }
}