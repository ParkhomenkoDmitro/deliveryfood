package com.parkhomenko.common.domain.discount.test;

import com.parkhomenko.common.domain.*;
import com.parkhomenko.common.domain.discount.DiscountOne;
import com.parkhomenko.common.domain.discount.DiscountSupplier;
import com.parkhomenko.common.domain.special_types.Measure;
import com.parkhomenko.common.domain.special_types.money.CurrencyCode;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmount;
import com.parkhomenko.common.domain.special_types.money.MonetaryAmountFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dmytro Parkhomenko
 *         Created on 08.09.16.
 */

@RunWith(MockitoJUnitRunner.class)
public class OrderTester {
    private DiscountSupplier discountSupplierMock;
    private Warehouse warehouseMock;
    private Order order;
    private static ProductVersion[] products;
    static {
        ProductVersion fProduct = new ProductVersion();
        fProduct.setBarcode("barcode1");
        fProduct.setShipping(new ProductParameter(1.0, Measure.PS));

        ProductVersion sProduct = new ProductVersion();
        sProduct.setBarcode("barcode2");
        sProduct.setShipping(new ProductParameter(1.0, Measure.PS));

        products = new ProductVersion[] {fProduct, sProduct};
    }

    @Before
    public void setUp() {
        discountSupplierMock = mock(DiscountSupplier.class);

        order = new Order();

        Address clientAddress = new Address();
        order.setClientAddress(clientAddress);

        warehouseMock = mock(Warehouse.class);
        when(warehouseMock.getAddress()).thenReturn(new Address());
        order.setWarehouse(warehouseMock);

        order.setCreatedDateTime(LocalDateTime.MAX);
    }

    @Test
    public void order_price_with_two_products_that_both_have_discount_type_one() {
        ProductVersion fProduct = pushProduct2Order(0, 10);
        ProductVersion sProduct = pushProduct2Order(1, 1);

        mockDiscountOneBehaviour(fProduct, MonetaryAmountFactory.getMonetaryAmount(BigDecimal.ONE, CurrencyCode.USD));
        mockDiscountOneBehaviour(sProduct, MonetaryAmountFactory.getMonetaryAmount(BigDecimal.ONE, CurrencyCode.USD));

        order.calculatePrice(discountSupplierMock);

        MonetaryAmount actual = order.getProductsPrice();
        MonetaryAmount expected = MonetaryAmountFactory.getMonetaryAmount(BigDecimal.TEN.add(BigDecimal.ONE), CurrencyCode.USD);

        Assert.assertEquals(expected, actual);
    }

    private void mockDiscountOneBehaviour(Product product, MonetaryAmount discountOnePrice) {
        when(discountSupplierMock.fetch(product, order.getCreatedDateTime())).thenAnswer((var1) -> {
            DiscountOne discount = mock(DiscountOne.class);
            when(discount.getPrice()).thenReturn(discountOnePrice);
            when(discount.isValid(order.getCreatedDateTime())).thenReturn(true);
            return discount;
        });
    }

    private ProductVersion pushProduct2Order(int index, int count) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setCount(count);
        orderProduct.setOrder(order);
        orderProduct.setProduct(products[index]);
        order.getOrderProducts().add(orderProduct);
        return products[index];
    }
}