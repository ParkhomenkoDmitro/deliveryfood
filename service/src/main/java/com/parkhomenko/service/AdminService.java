package com.parkhomenko.service;

import com.parkhomenko.common.domain.Admin;

import java.util.List;

/**
 * @author Dmytro Parkhomenko
 *         Created on 19.08.16.
 */

public interface AdminService extends CommonUserService<Admin> {
    List<Admin> search(Admin admin);
}
