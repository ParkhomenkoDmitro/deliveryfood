package com.parkhomenko.persistence.dao;

import com.parkhomenko.common.domain.User;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Dmytro Parkhomenko
 * Created on 19.07.16.
 */

@NoRepositoryBean
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
