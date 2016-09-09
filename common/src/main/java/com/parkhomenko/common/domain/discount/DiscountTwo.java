package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.Order;
import com.parkhomenko.common.domain.OrderProduct;
import com.parkhomenko.common.domain.Product;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmount;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmountFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Class that represent DiscountTwo discount type logic and will be applied to all products in order that:
 * supported discount of this type and it is the first discount that will be applied to this product.
 * DiscountTwo discount type logic: every third minimal price product is a free.
 *
 * @author Dmytro Parkhomenko
 * Created on 12.08.16.
 */

public class DiscountTwo extends Discount {
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
    protected void calculate(Order order) {
        Set<OrderProduct> noAppliedDiscountsProducts = getNoAppliedDiscountsProducts(order.getOrderProducts());
        Map<OrderProduct, DiscountTwo> map = findAppropriateOrderProducts(noAppliedDiscountsProducts, order.getCreatedDateTime());
        Set<DiscountTwo> discountsTypeTwo = new HashSet<>(map.values());
        discountsTypeTwo.forEach(discount -> doLogic(filterByDiscount(discount, map)));
    }

    private Map<OrderProduct, DiscountTwo> findAppropriateOrderProducts(Set<OrderProduct> products, LocalDateTime orderCreationDateTime) {
        Map<OrderProduct, DiscountTwo> discounts = new HashMap<>();
        for(OrderProduct orderProduct : products) {
            Discount discount = fetcher.fetch(orderProduct.getProduct(), orderCreationDateTime);
            if(discount.isValid(orderCreationDateTime)) {
                discounts.put(orderProduct, (DiscountTwo) discount);
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
                MonetaryAmount zeroPrice = MonetaryAmountFactory.getUSDZeroMonetaryAmount();
                applyDiscount(discount, orderProduct, freeProductQuantity, zeroPrice, zeroPrice);
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