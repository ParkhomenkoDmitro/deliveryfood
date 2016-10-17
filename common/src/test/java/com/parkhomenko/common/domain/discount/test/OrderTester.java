package com.parkhomenko.common.domain.discount.test;

import com.parkhomenko.common.domain.*;
import com.parkhomenko.common.domain.discount.Discount;
import com.parkhomenko.common.domain.discount.DiscountOne;
import com.parkhomenko.common.domain.discount.DiscountTwo;
import com.parkhomenko.common.domain.special_types.Measure;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmount;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmountFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmytro Parkhomenko
 *         Created on 08.09.16.
 */

public class OrderTester {
    private Order order;

    @Before
    public void setUp() {
        order = new Order();

        //Client address block
        Address clientAddress = new Address();
        order.setClientAddress(clientAddress);

        //Warehouse block
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress(new Address());
        order.setWarehouse(warehouse);
    }

    @Test
    public void order_price_with_products_that_without_discounts() {
        //prepare
        OrderProduct oProduct1 = buildOrderProductNoDiscounts(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                2, // product price
                10 // product count
        );

        OrderProduct oProduct2 = buildOrderProductNoDiscounts(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                4, // product price
                6 // product count
        );

        OrderProduct oProduct3 = buildOrderProductNoDiscounts(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                6, // product price
                2 // product count
        );

        Set<OrderProduct> ops = new HashSet<>();
        ops.add(oProduct1);
        ops.add(oProduct2);
        ops.add(oProduct3);
        order.setOrderProducts(ops);

        //Start logic
        order.calculatePrice();

        //Test
        MonetaryAmount expected = MonetaryAmountFactory.money(56);
        MonetaryAmount actual = order.getProductsPrice();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void order_price_with_products_that_have_only_discount_type_one() {
        //prepare
        OrderProduct oProduct1 = buildOrderProductWithDiscountOne(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                2, // product price
                1, // discount product price
                10 // product count
        );

        OrderProduct oProduct2 = buildOrderProductWithDiscountOne(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                4, // product price
                2, // discount product price
                6 // product count
        );

        OrderProduct oProduct3 = buildOrderProductWithDiscountOne(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                6, // product price
                4, // discount product price
                2 // product count
        );

        Set<OrderProduct> ops = new HashSet<>();
        ops.add(oProduct1);
        ops.add(oProduct2);
        ops.add(oProduct3);
        order.setOrderProducts(ops);

        //Start logic
        order.calculatePrice();

        //Test
        MonetaryAmount expected = MonetaryAmountFactory.money(30);
        MonetaryAmount actual = order.getProductsPrice();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void order_price_with_products_that_have_only_discount_type_two() {
        //prepare
        Discount discount = buildDiscountTwo();

        OrderProduct oProduct1 = buildOrderProductWithDiscountTwo(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                2, // product price
                10, // product count
                discount
        );

        OrderProduct oProduct2 = buildOrderProductWithDiscountTwo(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                4, // product price
                6, // product count
                discount
        );

        OrderProduct oProduct3 = buildOrderProductWithDiscountTwo(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                6, // product price
                2, // product count
                discount
        );

        Set<OrderProduct> ops = new HashSet<>();
        ops.add(oProduct1);
        ops.add(oProduct2);
        ops.add(oProduct3);
        order.setOrderProducts(ops);

        //Start logic
        order.calculatePrice();

        //Test
        MonetaryAmount expected = MonetaryAmountFactory.money(36);
        MonetaryAmount actual = order.getProductsPrice();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void order_price_with_products_that_have_discount_type_one_and_two() {
        //prepare
        OrderProduct oProduct1 = buildOrderProductWithDiscountOne(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                2, // product price
                1, // discount product price
                10 // product count
        );

        OrderProduct oProduct2 = buildOrderProductNoDiscounts(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                4, // product price
                6 // product count
        );

        Discount discount = buildDiscountTwo();
        OrderProduct oProduct3 = buildOrderProductWithDiscountTwo(
                1.0, // product shipping value
                Measure.PS, // product shipping measure type
                6, // product price
                3, // product count
                discount
        );

        Set<OrderProduct> ops = new HashSet<>();
        ops.add(oProduct1);
        ops.add(oProduct2);
        ops.add(oProduct3);
        order.setOrderProducts(ops);

        //Start logic
        order.calculatePrice();

        //Test
        MonetaryAmount expected = MonetaryAmountFactory.money(46);
        MonetaryAmount actual = order.getProductsPrice();
        Assert.assertEquals(expected, actual);
    }

    private OrderProduct buildOrderProductWithDiscountTwo(
            double shippingValue,
            Measure shippingMeasure,
            int price,
            int orderProductCount,
            Discount discount
    ) {
        //shipping
        ProductParameter shipping = new ProductParameter(shippingValue, shippingMeasure);
        //product
        ProductVersion prod1 = buildProduct(
                MonetaryAmountFactory.money(price),
                shipping
        );
        //discount
        prod1.setDiscount(discount);
        //order product
        return buildOrderProduct(
                prod1,
                orderProductCount
        );
    }

    private OrderProduct buildOrderProductWithDiscountOne(
            double shippingValue,
            Measure shippingMeasure,
            int price,
            int discountPrice,
            int orderProductCount
    ) {
        //shipping
        ProductParameter shipping = new ProductParameter(shippingValue, shippingMeasure);
        //product
        ProductVersion prod1 = buildProduct(
                MonetaryAmountFactory.money(price),
                shipping
        );
        //discount
        Discount discount = buildDiscountOne(
                MonetaryAmountFactory.money(discountPrice)
        );
        prod1.setDiscount(discount);
        //order product
        return buildOrderProduct(
                prod1,
                orderProductCount
        );
    }

    private OrderProduct buildOrderProductNoDiscounts(
            double shippingValue,
            Measure shippingMeasure,
            int price,
            int orderProductCount
    ) {
        //shipping
        ProductParameter shipping = new ProductParameter(shippingValue, shippingMeasure);
        //product
        ProductVersion prod1 = buildProduct(
                MonetaryAmountFactory.money(price),
                shipping
        );
        //order product
        return buildOrderProduct(
                prod1,
                orderProductCount
        );
    }

    private OrderProduct buildOrderProduct(ProductVersion product, int count) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setCount(count);
        orderProduct.setProduct(product);
        return orderProduct;
    }

    private ProductVersion buildProduct(MonetaryAmount price, ProductParameter shipping) {
        ProductVersion product = new ProductVersion();
        product.setBarcode(String.valueOf(System.nanoTime()));
        product.setPrice(price);
        product.setShipping(shipping);
        return product;
    }

    private Discount buildDiscountOne(MonetaryAmount price) {
        DiscountOne discount = new DiscountOne();
        discount.setPrice(price);
        return discount;
    }

    private Discount buildDiscountTwo() {
        DiscountTwo discount = new DiscountTwo();
        discount.setCode(String.valueOf(System.nanoTime()));
        return discount;
    }
}