package com.parkhomenko.common.domain.discount.test;

import com.parkhomenko.common.domain.*;
import com.parkhomenko.common.domain.discount.DiscountOne;
import com.parkhomenko.common.domain.discount.DiscountSupplier;
import com.parkhomenko.common.domain.special_types.DecimalMonetaryAmount;
import com.parkhomenko.common.domain.special_types.MonetaryAmount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dmytro Parkhomenko
 *         Created on 08.09.16.
 */

@RunWith(MockitoJUnitRunner.class)
public class OrderPriceWithDiscountTester {

    private Order order;
    private DiscountSupplier discountSupplier;
    private Product firstProduct;
    private DiscountOne discountOneForFirstProduct;
    private Address clientAddress;
    private Warehouse warehouse;

    @Before
    public void setUp(){
        discountSupplier = mock(DiscountSupplier.class);
        firstProduct = mock(Product.class);
        discountOneForFirstProduct = mock(DiscountOne.class);

        order = new Order();

        OrderProduct firstProductOrderProduct = new OrderProduct();
        firstProductOrderProduct.setCount(10);
        firstProductOrderProduct.setOrder(order);
        firstProductOrderProduct.setProduct(firstProduct);

        Set<OrderProduct> orderProducts = new HashSet<>();
        orderProducts.add(firstProductOrderProduct);
        order.setOrderProducts(orderProducts);

        clientAddress = mock(Address.class);
        order.setClientAddress(clientAddress);

        warehouse = mock(Warehouse.class);
        order.setWarehouse(warehouse);

        order.setCreatedDateTime(LocalDateTime.MAX);
    }

    @Test
    public void test(){
        DecimalMonetaryAmount usd = new DecimalMonetaryAmount(BigDecimal.ONE, Currency.getInstance("USD"));

        when(firstProduct.calcPrice(usd, 10)).thenReturn(usd);

        when(warehouse.getAddress()).thenReturn(new Address());

        when(discountOneForFirstProduct.getPrice()).thenReturn(usd);

        when(discountOneForFirstProduct.isValid(LocalDateTime.MAX)).thenReturn(true);

        when(discountSupplier.fetch(firstProduct, LocalDateTime.MAX)).thenReturn(discountOneForFirstProduct);

        order.calculatePrice(discountSupplier);

        MonetaryAmount productsPrice = order.getProductsPrice();

        MonetaryAmount truePrice = new DecimalMonetaryAmount(BigDecimal.ONE, Currency.getInstance("USD"));

        Assert.assertEquals(productsPrice, truePrice);
    }
}