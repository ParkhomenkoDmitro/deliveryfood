package com.parkhomenko.common.domain.util;

/**
 * @author Dmytro Parkhomenko
 *         Created on 24.10.16.
 */

public class View {

    public interface Summary {}
    public interface UserDetails extends Summary {}
    public interface WarehouseDetails extends Summary {}
    public interface AddressDetails extends Summary {}
    public interface CityDetails extends Summary {}
}
