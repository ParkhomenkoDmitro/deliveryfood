package com.parkhomenko.service;

import com.parkhomenko.common.domain.User;

/**
 * @author Dmytro Parkhomenko
 * Created on 19.07.16.
 */

public interface UserService {
    User findOne(Long aLong);
    void deleteAll();
    User save(User user);
}
