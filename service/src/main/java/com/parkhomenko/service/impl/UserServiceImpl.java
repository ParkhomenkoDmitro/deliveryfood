package com.parkhomenko.service.impl;

import com.parkhomenko.common.domain.User;
import com.parkhomenko.persistence.dao.UserRepository;
import com.parkhomenko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dmytro on 19.07.16.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User findOne(Long aLong) {
        return userRepository.findOne(aLong);
    }
}