package com.parkhomenko.rout.controller;

import com.parkhomenko.common.domain.Admin;
import com.parkhomenko.rout.util.ExceptionUtil;
import com.parkhomenko.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dmytro Parkhomenko
 * Created on 19.07.16.
 */

@RestController
public class AdminController {

    private AdminService service;

    @Autowired
    public void setService(AdminService service) {
        this.service = service;
    }

    @RequestMapping(value = "/admins/{id}", method = RequestMethod.GET)
    public Admin getById(@PathVariable Long id) {
        Admin admin = service.findOne(id);
        ExceptionUtil.check(admin, id);
        return admin;
    }

    @RequestMapping(value = "/admins", method = RequestMethod.POST)
    public Long save(@RequestBody Admin user) {
        return service.save(user).getId();
    }

    @RequestMapping(value = "/admins", method = RequestMethod.DELETE)
    public void deleteAll() {
        service.deleteAll();
    }

    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    public List<Admin> findAll(Pageable pageable) throws MissingServletRequestParameterException {
        ExceptionUtil.check(pageable);
        return service.findAll(pageable);
    }

    @RequestMapping(value = "/admins/all", method = RequestMethod.GET)
    public List<Admin> findAll() {
        return service.findAll();
    }
}
