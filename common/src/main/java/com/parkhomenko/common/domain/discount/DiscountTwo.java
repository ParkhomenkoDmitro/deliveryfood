package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.OrderProduct;
import com.parkhomenko.common.domain.Product;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmount;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmountFactory;

import java.util.*;

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

    public static void calculate(Set<OrderProduct> orderProducts) {
        Set<OrderProduct> orderProductsWithDiscountTwo = new HashSet<>();
        Set<DiscountTwo> discounts = new HashSet<>();

        for (OrderProduct item : orderProducts) {
            Discount discount = item.getProduct().getDiscount();
            if(discount instanceof DiscountTwo) {
                orderProductsWithDiscountTwo.add(item);
                discounts.add((DiscountTwo) discount);
            }
        }

        discounts.forEach(discount -> doLogic(filterByDiscount(discount, orderProductsWithDiscountTwo)));
    }

    private static Set<OrderProduct> filterByDiscount(DiscountTwo discount, Set<OrderProduct> set) {
        Set<OrderProduct> result = new HashSet<>();

        for (OrderProduct item : set) {

            DiscountTwo currentDiscount = (DiscountTwo) item.getProduct().getDiscount();

            if(discount.equals(currentDiscount)) {
                result.add(item);
            }
        }

        return  result;
    }

    private static void doLogic(Set<OrderProduct> appropriates) {
        final int EVERY_FREE_PRODUCT_INDEX = 3;

        List<OrderProduct> orderProducts = new ArrayList<>(appropriates);

        orderProducts.sort(comparing(OrderProduct::getProduct, comparing(Product::getPrice)));

        for(int allProductsQuantity = 0, freeProductsQuantity = 0, i = 0; i < orderProducts.size(); i++) {
            OrderProduct orderProduct = orderProducts.get(i);
            int productQuantity = orderProduct.getCount();

            allProductsQuantity += productQuantity;
            int freeProductQuantity = allProductsQuantity / EVERY_FREE_PRODUCT_INDEX - freeProductsQuantity;
            freeProductsQuantity += freeProductQuantity;

            if(freeProductQuantity > 0) {
                int payProductQuantity = productQuantity - freeProductQuantity;
                DiscountTwo discount = (DiscountTwo) orderProduct.getProduct().getDiscount();
                Product product = orderProduct.getProduct();
                applyDiscount(discount, orderProduct, payProductQuantity, product.getPrice(), product.calcPrice(payProductQuantity));
                MonetaryAmount zeroPrice = MonetaryAmountFactory.ZERO;
                applyDiscount(discount, orderProduct, freeProductQuantity, zeroPrice, zeroPrice);
            }
        }
    }

    private static void applyDiscount(DiscountTwo discount,
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