package com.parkhomenko.rout.controller;

import com.parkhomenko.rout.util.NotFoundException;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Dmytro Parkhomenko
 *         Created on 25.08.16.
 */

@ControllerAdvice(basePackageClasses = AdminController.class)
public class AdminControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        DefaultErrorAttributes defaultErrorAttributes = new DefaultErrorAttributes();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> body = defaultErrorAttributes.getErrorAttributes(servletRequestAttributes, false);
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
