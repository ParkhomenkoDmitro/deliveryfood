package com.parkhomenko.rout.controller;

import com.parkhomenko.common.domain.Admin;
import com.parkhomenko.persistence.dao.util.LoggingMessage;
import com.parkhomenko.rout.util.CacheUtil;
import com.parkhomenko.service.AdminService;
import com.parkhomenko.service.impl.SimpleInMemoryCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;

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
        return service.findOne(id);
    }

    @RequestMapping(value = "/admins/{id}", method = RequestMethod.PUT)
    public void update(
            HttpServletRequest req,
            @PathVariable Long id,
            @RequestBody Admin admin,
            @RequestParam(name = "apptx", required = false, defaultValue = "false") Boolean isApplicationTransaction) {

        if(isApplicationTransaction) {
            String cacheKey = CacheUtil.getCacheKey(req);
            Admin adminFromCache = (Admin) SimpleInMemoryCache.get(cacheKey);

            Objects.requireNonNull(adminFromCache, "serialized obj with cache key = " + cacheKey + " not found in cache");

            adminFromCache.update(admin);
            service.save(adminFromCache);
            SimpleInMemoryCache.remove(cacheKey);
        } else {
            Admin adminFromDb = service.findOne(id);

            Objects.requireNonNull(adminFromDb, LoggingMessage.logMessage(id));

            adminFromDb.update(admin);
            service.save(adminFromDb);
        }
    }

    @RequestMapping(value = "/admins", method = RequestMethod.POST)
    public Long save(@RequestBody Admin user) {
        return service.save(user);
    }

    @RequestMapping(value = "/admins", method = RequestMethod.DELETE)
    public void deleteAll() {
        service.deleteAll();
    }

    @RequestMapping(value = "/admins/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.delete(id);
    }


    /**
     * @param pageable must contains Sort object
     * @return collection of admins
     */
    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    public Iterable<Admin> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @RequestMapping(value = "/admins/count", method = RequestMethod.GET)
    public long getCount() {
        return service.count();
    }

    @RequestMapping(value = "/admins/set", method = RequestMethod.DELETE)
    public void deleteByIds(@RequestBody Set<Long> ids) {
        service.delete(ids);
    }
}
