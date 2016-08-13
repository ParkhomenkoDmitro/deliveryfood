package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.Client;
import com.parkhomenko.common.domain.Order;
import com.parkhomenko.common.domain.OrderProduct;
import com.parkhomenko.common.domain.Product;
import com.parkhomenko.common.domain.special_types.DiscountType;
import com.parkhomenko.common.domain.util.MonetaryAmount;
import com.parkhomenko.common.domain.util.MonetaryAmountFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dmytro on 12.08.16.
 */
public class DiscountTypeTwo extends AbstractDiscount {
    private Set<Product> products = new HashSet<>();

    public DiscountTypeTwo() {
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setFetcher(DiscountFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    protected void calculate(Client client, Order order) {
        Set<OrderProduct> products = order.getOrderProducts();
        Set<String> discountCodes = new HashSet<>();
        for (OrderProduct orderProduct : products) {
            Product product = orderProduct.getProduct();
            DiscountTypeTwo discount = fetcher.fetchDiscountTypeTwo(product);
            List<AppliedDiscount> appliedDiscounts = orderProduct.getAppliedDiscounts();
            if(discount != null && appliedDiscounts == null) {
                discountCodes.add(discount.getCode());
            }
        }
        discountCodes.forEach(code -> doLogic(findAppropriate(code, products)));
    }

    private List<OrderProduct> findAppropriate(String code, Set<OrderProduct> products) {
        List<OrderProduct> appropriates = new ArrayList<>();
        for (OrderProduct orderProduct : products) {
            Product product = orderProduct.getProduct();
            DiscountTypeTwo discount = fetcher.fetchDiscountTypeTwo(product);
            List<AppliedDiscount> appliedDiscounts = orderProduct.getAppliedDiscounts();
            if (discount != null && appliedDiscounts == null && discount.getCode().equals(code)) {
                //продукт підтримує скидку даного типу та до нього не застосовано ще жодної скидки
                appropriates.add(orderProduct);
            }
        }
        return appropriates;
    }

    private void doLogic(List<OrderProduct> appropriates) {
        final int EVERY_FREE_PRODUCT_INDEX = 3;

        appropriates.sort((p1, p2) -> p1.getProduct().getPrice().compareTo(p2.getProduct().getPrice()));

        for(int allProductsQuantity = 0, freeProductsQuantity = 0, i = 0; i < appropriates.size(); i++) {
            OrderProduct item = appropriates.get(i);
            int productQuantity = item.getCount();

            allProductsQuantity += productQuantity;
            int freeProductQuantity = allProductsQuantity / EVERY_FREE_PRODUCT_INDEX - freeProductsQuantity;
            freeProductsQuantity += freeProductQuantity;

            if(freeProductQuantity > 0) {
                int payProductQuantity = productQuantity - freeProductQuantity;
                Product product = item.getProduct();
                DiscountTypeTwo discount = fetcher.fetchDiscountTypeTwo(product);
                apply(discount, item, payProductQuantity, product.getPrice(), product.calcPrice(payProductQuantity));
                apply(discount, item, freeProductQuantity, MonetaryAmountFactory.ZERO, MonetaryAmountFactory.ZERO);
            }
        }
    }

    private void apply(DiscountTypeTwo discount,
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