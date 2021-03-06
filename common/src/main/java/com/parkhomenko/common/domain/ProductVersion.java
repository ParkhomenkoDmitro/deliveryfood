package com.parkhomenko.common.domain;

/**
 * @author Dmytro Parkhomenko
 * Created on 10.08.16.
 */

public class ProductVersion extends Product {
    private ProductOrigin product;

    public ProductVersion() {
    }

    public ProductOrigin getProduct() {
        return product;
    }

    public void setProduct(ProductOrigin product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProductVersion that = (ProductVersion) o;

        if (getBarcode() != null ? !getBarcode().equals(that.getBarcode()) : that.getBarcode() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getPrice() != null ? !getPrice().equals(that.getPrice()) : that.getPrice() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getBrand() != null ? !getBrand().equals(that.getBrand()) : that.getBrand() != null) return false;
        if (getManufacturer() != null ? !getManufacturer().equals(that.getManufacturer()) : that.getManufacturer() != null)
            return false;
        if (getInstructions() != null ? !getInstructions().equals(that.getInstructions()) : that.getInstructions() != null)
            return false;
        if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
        if (getAllergic() != null ? !getAllergic().equals(that.getAllergic()) : that.getAllergic() != null)
            return false;
        if (getAlias() != null ? !getAlias().equals(that.getAlias()) : that.getAlias() != null)
            return false;
        return getCore() != null ? getCore().equals(that.getCore()) : that.getCore() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getBarcode() != null ? getBarcode().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getBrand() != null ? getBrand().hashCode() : 0);
        result = 31 * result + (getManufacturer() != null ? getManufacturer().hashCode() : 0);
        result = 31 * result + (getInstructions() != null ? getInstructions().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getAllergic() != null ? getAllergic().hashCode() : 0);
        result = 31 * result + (getAlias() != null ? getAlias().hashCode() : 0);
        result = 31 * result + (getCore() != null ? getCore().hashCode() : 0);
        return result;
    }
}
