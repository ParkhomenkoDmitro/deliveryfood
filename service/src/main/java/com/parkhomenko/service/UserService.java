package com.parkhomenko.service;

import com.parkhomenko.common.domain.User;

/**
 * Created by dmytro on 19.07.16.
 */
public interface UserService {
    User findOne(Long aLong);
}
