package com.parkhomenko.persistence.dao;

import com.parkhomenko.common.domain.Admin;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Dmytro Parkhomenko
 *         Created on 18.08.16.
 */

@NoRepositoryBean
public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {
}
