package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.*;
import com.parkhomenko.common.domain.special_types.DiscountType;
import com.parkhomenko.common.domain.util.MonetaryAmount;

import java.util.List;
import java.util.Set;

/**
 * Created by dmytro on 12.08.16.
 */
public class DiscountTypeOne extends AbstractDiscount {
    private MonetaryAmount price;
    private Product product;

    public DiscountTypeOne() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    protected void calculate(Client client, Order order) {
        Set<OrderProduct> products = order.getOrderProducts();
        for (OrderProduct orderProduct : products) {
            Product product = orderProduct.getProduct();
            DiscountTypeOne discount = fetcher.fetchDiscountTypeOne(product);
            List<AppliedDiscount> appliedDiscounts = orderProduct.getAppliedDiscounts();
            if (discount != null && appliedDiscounts == null) {
                //продукт підтримує скидку даного типу та до нього не застосовано ще жодної скидки
                AppliedDiscount appliedDiscount = new AppliedDiscount();

                appliedDiscount.setCount(orderProduct.getCount());
                appliedDiscount.setDiscountCode(discount.getCode());
                appliedDiscount.setType(DiscountType.TYPE_ONE);
                appliedDiscount.setPriceForOne(discount.getPrice());
                appliedDiscount.setTotalPrice(product.calcPrice(discount.getPrice(), orderProduct.getCount()));

                appliedDiscount.setOrderProduct(orderProduct);
                appliedDiscounts.add(appliedDiscount);
            }
        }
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }
}