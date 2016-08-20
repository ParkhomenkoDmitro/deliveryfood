package com.parkhomenko.rout.controller;

import com.parkhomenko.common.domain.Admin;
import com.parkhomenko.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public HttpEntity<Admin> getUser(@PathVariable Long id) {
        Admin user = service.findOne(id);
        return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(user, HttpStatus.OK);
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
    public List<Admin> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @RequestMapping(value = "/admins/all", method = RequestMethod.GET)
    public List<Admin> findAll() {
        return service.findAll();
    }
}
