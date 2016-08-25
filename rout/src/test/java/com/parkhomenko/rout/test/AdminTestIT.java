package com.parkhomenko.rout.test;

import com.parkhomenko.common.domain.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author Dmytro Parkhomenko
 *         Created on 20.08.16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("development")
public class AdminTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
    })
    public void get_all_admins() {
        ResponseEntity<Admin[]> responseEntity = restTemplate.getForEntity("/admins/all", Admin[].class);
        Admin[] admins = responseEntity.getBody();

        assertTrue("List is empty", admins.length > 0);
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
    })
    public void get_admins_with_paging() {
        ResponseEntity<Admin[]> responseEntity = restTemplate.getForEntity("/admins?page=0&size=10&sort=id,desc", Admin[].class);
        Admin[] admins = responseEntity.getBody();

        assertTrue("List is empty", admins.length > 0);
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
    })
    public void delete_all() {
        restTemplate.delete("/admins");
        ResponseEntity<Admin[]> responseEntity = restTemplate.getForEntity("/admins/all", Admin[].class);
        Admin[] admins = responseEntity.getBody();

        assertTrue("Not deleted", admins.length == 0);
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
    })
    public void get_by_id() {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", "1");
        ResponseEntity<Admin> responseEntity = restTemplate.getForEntity("/admins/{id}", Admin.class, urlVariables);
        Admin admin = responseEntity.getBody();

        assertTrue("Id mismatch", admin.getId().equals(1L));
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
    public void save_one_admin() {
        Admin newAdmin = new Admin();
        newAdmin.setBlocked(false);
        newAdmin.setLogin("test");
        newAdmin.setPassword("test");
        newAdmin.setName("Donald Duck");
        newAdmin.setPhone("+3805555555");

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity("/admins", newAdmin, Long.class);
        Long newId = responseEntity.getBody();

        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", newId.toString());

        ResponseEntity<Admin> responseEntity2 = restTemplate.getForEntity("/admins/{id}", Admin.class, urlVariables);
        Admin admin = responseEntity2.getBody();

        assertTrue("Id mismatch", admin.getId().equals(newId));
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }
}
