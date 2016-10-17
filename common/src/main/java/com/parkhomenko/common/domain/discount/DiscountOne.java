package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.OrderProduct;
import com.parkhomenko.common.domain.Product;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmount;

import java.util.Set;

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

    public static void calculate(Set<OrderProduct> orderProducts) {
        for (OrderProduct item : orderProducts) {
            Discount discount = item.getProduct().getDiscount();
            if(discount instanceof DiscountOne) {
                applyDiscount(item);
            }
        }
    }

    private static void applyDiscount(OrderProduct orderProduct) {
        AppliedDiscount appliedDiscount = new AppliedDiscount();
        Product product = orderProduct.getProduct();
        appliedDiscount.setCount(orderProduct.getCount());

        DiscountOne discount = (DiscountOne) orderProduct.getProduct().getDiscount();

        appliedDiscount.setDiscountCode(discount.getCode());
        appliedDiscount.setType(DiscountType.TYPE_ONE);
        appliedDiscount.setPriceForOne(discount.getPrice());
        appliedDiscount.setTotalPrice(product.calcPrice(discount.getPrice(), orderProduct.getCount()));
        appliedDiscount.setOrderProduct(orderProduct);
        orderProduct.getAppliedDiscounts().add(appliedDiscount);
    }
}