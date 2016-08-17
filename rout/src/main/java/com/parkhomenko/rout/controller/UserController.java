package com.parkhomenko.rout.controller;

import com.parkhomenko.common.domain.User;
import com.parkhomenko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dmytro Parkhomenko
 * Created on 19.07.16.
 */

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public HttpEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findOne(id);
        return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Long save(@RequestBody User user) {
        return userService.save(user).getId();
    }

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public void deleteAll() {
        userService.deleteAll();
    }
}
