package com.parkhomenko.common.domain;

import com.parkhomenko.common.domain.util.MonetaryAmount;
import com.parkhomenko.common.domain.util.MonetaryAmountFactory;

/**
 * @author Dmytro Parkhomenko
 *         Created on 30.08.16.
 */

public class Traffic {

    public static MonetaryAmount calculateTrafficCoast(Address start, Address finish) {
        //TODO traffic logic here
        return MonetaryAmountFactory.ZERO;
    }
}
