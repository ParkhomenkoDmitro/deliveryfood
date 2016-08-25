package com.parkhomenko.rout.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.Optional;

/**
 * @author Dmytro Parkhomenko
 *         Created on 25.08.16.
 */

public class ExceptionUtil {
    public static void check(Pageable pageable) throws MissingServletRequestParameterException {
        Optional<Sort> sortOptional = Optional.ofNullable(pageable.getSort());
        if(!sortOptional.isPresent()) {
            throw new MissingServletRequestParameterException("sort", "org.springframework.data.domain.Sort");
        }
    }

    public static void check(Object entity, Long id) {
        Optional<Object> optional = Optional.ofNullable(entity);
        if(!optional.isPresent()) {
            throw new NotFoundException("Entity with id = " + id + " not found");
        }
    }
}
