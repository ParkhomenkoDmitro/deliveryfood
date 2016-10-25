package com.parkhomenko.rout.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkhomenko.common.domain.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Dmytro Parkhomenko
 *         Created on 20.08.16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"hibernate.search.index=false"}
)
@ActiveProfiles("development")
public class AdminTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void get_admins_with_paging() {
        ResponseEntity<Admin[]> responseEntity = restTemplate.getForEntity("/admins?page=0&size=10&sort=id,desc", Admin[].class);
        Admin[] admins = responseEntity.getBody();

        assertTrue("List is empty", admins.length > 0);
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void delete_all() {
        restTemplate.delete("/admins");
        ResponseEntity<Long> responseEntity = restTemplate.getForEntity("/admins/count", Long.class);
        long count = responseEntity.getBody();

        assertTrue("Not deleted", count == 0);
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void count_all() {
        ResponseEntity<Long> responseEntity = restTemplate.getForEntity("/admins/count", Long.class);
        long count = responseEntity.getBody();

        assertTrue("Not correct cont", count == 1);
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void get_by_id() {
        Long id = 1L;
        ResponseEntity<Admin> responseEntity = restTemplate.getForEntity("/admins/{id}", Admin.class, id);
        Admin admin = responseEntity.getBody();

        assertTrue("Id mismatch", id.equals(admin.getId()));
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    public void add_one_admin() {
        Admin newAdmin = buildAdmin();

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity("/admins", newAdmin, Long.class);
        Long id = responseEntity.getBody();

        assertTrue("Id isinvalud", id > 0);
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void update_one_admin() {
        Long id = 1L;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setName("Jack Daniels");

        restTemplate.put("/admins/{id}", admin, id);

        ResponseEntity<Admin> responseEntity = restTemplate.getForEntity("/admins/{id}", Admin.class, id);
        Admin updatedAdmin = responseEntity.getBody();

        assertTrue("Names mismatch", admin.getName().equals(updatedAdmin.getName()));
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void get_update_one_admin_client_transaction_test() {
        Long id = 1L;

        //OPEN APPLICATION TRANSACTION
        ResponseEntity<Admin> responseEntity = restTemplate.getForEntity("/admins/{id}?apptx=1", Admin.class, id);
        Admin admin = responseEntity.getBody();

        admin.setName("Jack Daniels");

        //CLOSE APPLICATION TRANSACTION
        restTemplate.put("/admins/{id}?apptx=1", admin, id);

        ResponseEntity<Admin> responseEntity2 = restTemplate.getForEntity("/admins/{id}", Admin.class, id);
        Admin updatedAdmin = responseEntity2.getBody();

        assertTrue("Names mismatch", admin.getName().equals(updatedAdmin.getName()));
        assertTrue("Status is not OK", responseEntity2.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void get_update_one_admin_client_transaction_failure_test() throws JsonProcessingException {
        Long id = 1L;

        //Now create Request body with the updated admin.
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Jack Daniels");
        requestBody.put("id", id);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), requestHeaders);

        ResponseEntity responseEntity = restTemplate.exchange("/admins/{id}?apptx=1", HttpMethod.PUT, httpEntity, Map.class, id);

        assertTrue("Status is not OK", responseEntity.getStatusCode().is5xxServerError());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v2.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void group_delete_by_ids() throws JsonProcessingException {
        //
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(Arrays.asList(1L, 2L)), requestHeaders);

        restTemplate.exchange("/admins/set", HttpMethod.DELETE, httpEntity, Map.class);

        ResponseEntity<Long> responseEntity2 = restTemplate.getForEntity("/admins/count", Long.class);
        long count = responseEntity2.getBody();

        assertTrue("Not correct cont", count == 0);
        assertTrue("Status is not OK", responseEntity2.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void search_test_for_find () {
        Admin adminOne = new Admin();
        adminOne.setName("Kris Evans");

        restTemplate.postForEntity("/admins", adminOne, Long.class);

        Admin adminTwo = new Admin();
        adminTwo.setName("Erik Bakhman");

        restTemplate.postForEntity("/admins", adminTwo, Long.class);

        Admin admin = new Admin();
        admin.setName("Erik");

        ResponseEntity<Admin[]> responseEntity = restTemplate.postForEntity("/admins/search", admin, Admin[].class);
        Admin[] searchResult = responseEntity.getBody();

        assertTrue("Not correct cont", searchResult.length == 1);
        assertTrue("Search mismatch", searchResult[0].getName().contains(admin.getName()));
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void search_test_for_not_find () {
        Admin adminOne = new Admin();
        adminOne.setName("Kris Evans");

        restTemplate.postForEntity("/admins", adminOne, Long.class);

        Admin adminTwo = new Admin();
        adminTwo.setName("Erik Bakhman");

        restTemplate.postForEntity("/admins", adminTwo, Long.class);

        Admin admin = new Admin();
        admin.setName("Parkhomenko");

        ResponseEntity<Admin[]> responseEntity = restTemplate.postForEntity("/admins/search", admin, Admin[].class);
        Admin[] searchResult = responseEntity.getBody();

        assertTrue("Not correct cont", searchResult.length == 0);
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    private Admin buildAdmin() {
        Admin newAdmin = new Admin();
        newAdmin.setBlocked(false);
        String time = String.valueOf(System.nanoTime());
        newAdmin.setLogin(time);
        newAdmin.setPassword("test");
        newAdmin.setName("Donald Duck" + time);
        newAdmin.setPhone("+3805555555");
        return newAdmin;
    }
}
