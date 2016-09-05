package com.parkhomenko.service.impl;

import com.parkhomenko.common.domain.discount.DiscountOne;
import com.parkhomenko.persistence.dao.DiscountRepository;
import com.parkhomenko.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dmytro Parkhomenko
 * Created on 13.08.16.
 */

@Service
public class DiscountServiceImpl implements DiscountService {

    private DiscountRepository<DiscountOne> oneDiscountRepository;

    @Autowired
    public void setOneDiscountRepository(DiscountRepository<DiscountOne> oneDiscountRepository) {
        this.oneDiscountRepository = oneDiscountRepository;
    }
}
