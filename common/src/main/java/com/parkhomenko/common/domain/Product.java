package com.parkhomenko.common.domain;

import com.parkhomenko.common.domain.special_types.money.MonetaryAmount;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import static com.parkhomenko.common.domain.special_types.Measure.PS;

/**
 * @author Dmytro Parkhomenko
 * Created on 09.08.16.
 */

public abstract class Product implements Serializable {
    private final static String ALIAS_DELIM = ";";
    private Long id;
    private String code;
    private String barcode;
    private String name;
    private Long availableCount;
    private MonetaryAmount price;
    private Set<Category> categories = new HashSet<>();
    private LocalDateTime createdDateTime;
    private String description;
    private String brand;
    private String manufacturer;
    private String instructions;
    private String country;
    private String allergic;
    private Set<String> alias = new HashSet<>();
    private String image;
    private ProductParameter core;
    private ProductParameter shipping;

    public Product() {
    }

    public MonetaryAmount calcPrice(MonetaryAmount price, int count) {
        MonetaryAmount result;
        if(shipping.getMeasure().equals(PS)) {
            result = price.multiply(count);
        } else {
            //TODO: common calc logic for all posible products according to count, price, core, shipping
            result = null;
        }
        return result;
    }

    public MonetaryAmount calcPrice(int count) {
        MonetaryAmount result;
        //TODO: common calc logic for all posible products according to
        result = price;
        return result;
    }

    public ProductParameter getShipping() {
        return shipping;
    }

    public void setShipping(ProductParameter shipping) {
        this.shipping = shipping;
    }

    public Long getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Long availableCount) {
        this.availableCount = availableCount;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAllergic() {
        return allergic;
    }

    public void setAllergic(String allergic) {
        this.allergic = allergic;
    }

    public String getAlias() {
        StringJoiner stringJoiner = new StringJoiner(ALIAS_DELIM);
        stringJoiner.setEmptyValue(StringUtils.EMPTY);
        alias.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    public void setAlias(String alias) {
        if(StringUtils.isEmpty(alias)) {
            return;
        }
        Set<String> result = new HashSet<>();
        StringTokenizer t = new StringTokenizer(alias, ALIAS_DELIM);
        while(t.hasMoreTokens()) {
            result.add(t.nextToken());
        }
        this.alias = result;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public ProductParameter getCore() {
        return core;
    }

    public void setCore(ProductParameter core) {
        this.core = core;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return code != null ? code.equals(product.code) : product.code == null;

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}
