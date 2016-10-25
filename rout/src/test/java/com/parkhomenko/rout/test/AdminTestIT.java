package com.parkhomenko.rout.test;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.List;

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

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void get_admins_with_paging() {
        //fetch
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
        //delete all
        restTemplate.delete("/admins");
        //fetch
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
        //fetch
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
        Long userId = 1L;
        //fetch
        ResponseEntity<Admin> responseEntity = restTemplate.getForEntity("/admins/{id}", Admin.class, userId);
        Admin admin = responseEntity.getBody();

        assertTrue("Id mismatch", userId.equals(admin.getId()));
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
        Long userId = 1L;
        //fetch
        ResponseEntity<Admin> responseEntity = restTemplate.getForEntity("/admins/{id}", Admin.class, userId);
        Admin fetchedAdmin = responseEntity.getBody();

        fetchedAdmin.setName(generateUserName());

        //update
        restTemplate.put("/admins/{id}", fetchedAdmin, userId);

        //fetch
        responseEntity = restTemplate.getForEntity("/admins/{id}", Admin.class, userId);
        Admin updatedAdmin = responseEntity.getBody();

        assertTrue("Names mismatch", fetchedAdmin.getName().equals(updatedAdmin.getName()));
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void get_update_one_admin_client_transaction_test() {
        Long id = 1L;
        //fetch
        ResponseEntity<Admin> responseEntity =
                restTemplate.getForEntity("/admins/{id}?apptx=1", Admin.class, id); //OPEN APPLICATION TRANSACTION
        Admin fetchedAdmin = responseEntity.getBody();

        //update
        fetchedAdmin.setName(generateUserName());

        //update
        restTemplate.put("/admins/{id}?apptx=1", fetchedAdmin, id); //CLOSE APPLICATION TRANSACTION

        //fetch
        responseEntity = restTemplate.getForEntity("/admins/{id}", Admin.class, id);
        Admin updatedAdmin = responseEntity.getBody();

        assertTrue("Names mismatch", fetchedAdmin.getName().equals(updatedAdmin.getName()));
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v1.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void get_update_one_admin_client_transaction_failure_test() throws JsonProcessingException {
        Long userId = 1L;
        //fetch without APPLICATION TRANSACTION
        ResponseEntity<Admin> responseEntity = restTemplate.getForEntity("/admins/{id}?apptx=0", Admin.class, userId);
        Admin fetchedAdmin = responseEntity.getBody();
        fetchedAdmin.setName(generateUserName());

        //Creating http entity object with request body and headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Admin> httpEntity = new HttpEntity<>(fetchedAdmin, requestHeaders);

        //update as with APPLICATION TRANSACTION
        ResponseEntity<Object> resEntity =
                restTemplate.exchange("/admins/{id}?apptx=1", HttpMethod.PUT, httpEntity, Object.class, userId);

        assertTrue("Status is not OK", resEntity.getStatusCode().is5xxServerError());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeRun_v2.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterRun.sql")
    })
    public void group_delete_by_ids() throws JsonProcessingException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List> httpEntity = new HttpEntity<>(Arrays.asList(1L, 2L), requestHeaders);

        //delete
        restTemplate.exchange("/admins/set", HttpMethod.DELETE, httpEntity, Object.class);

        //fetch
        ResponseEntity<Long> resEntity = restTemplate.getForEntity("/admins/count", Long.class);
        long count = resEntity.getBody();

        assertTrue("Not correct cont", count == 0);
        assertTrue("Status is not OK", resEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void search_test_for_find_by_one_field() {
        //add admin
        Admin adminOne = buildAdmin();
        restTemplate.postForEntity("/admins", adminOne, Long.class);
        //add admin
        Admin adminTwo = buildAdmin();
        restTemplate.postForEntity("/admins", adminTwo, Long.class);
        //search data
        Admin searchData = new Admin();
        String adminOneName = adminOne.getName().split(" ")[0];
        searchData.setName(adminOneName); //setting NAME as a part of the name for search

        ResponseEntity<Admin[]> responseEntity = restTemplate.postForEntity("/admins/search", searchData, Admin[].class);
        Admin[] searchResult = responseEntity.getBody();

        assertTrue("Not correct cont", searchResult.length == 1);
        assertTrue("Search mismatch", searchResult[0].getName().contains(searchData.getName()));
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void search_test_for_find_by_multiple_field() {
        //add admin
        Admin adminOne = buildAdmin();
        restTemplate.postForEntity("/admins", adminOne, Long.class);
        //add admin
        Admin adminTwo = buildAdmin();
        restTemplate.postForEntity("/admins", adminTwo, Long.class);
        //search data
        Admin searchData = new Admin();
        String adminOneSurname = adminOne.getName().split(" ")[1];
        searchData.setName(adminOneSurname); //setting SURNAME as a part of the name for search
        searchData.setPhone(adminOne.getPhone());

        ResponseEntity<Admin[]> responseEntity = restTemplate.postForEntity("/admins/search", searchData, Admin[].class);
        Admin[] searchResult = responseEntity.getBody();

        assertTrue("Not correct cont", searchResult.length == 1);
        assertTrue("Search mismatch", searchResult[0].getName().contains(searchData.getName()));
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void search_test_for_not_find () {
        //add admin
        Admin adminOne = buildAdmin();
        restTemplate.postForEntity("/admins", adminOne, Long.class);
        //add admin
        Admin adminTwo = buildAdmin();
        restTemplate.postForEntity("/admins", adminTwo, Long.class);
        //search data
        Admin searchData = new Admin();
        searchData.setName(generateUserName());

        ResponseEntity<Admin[]> responseEntity = restTemplate.postForEntity("/admins/search", searchData, Admin[].class);
        Admin[] searchResult = responseEntity.getBody();

        assertTrue("Not correct cont", searchResult.length == 0);
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }

    private Admin buildAdmin() {
        Admin newAdmin = new Admin();
        newAdmin.setBlocked(false);
        String time = String.valueOf(System.nanoTime());
        newAdmin.setLogin("DonaldTrumpLogin" + time);
        newAdmin.setPassword("DonaldTrumpPWD" + time);
        newAdmin.setName(generateUserName());
        newAdmin.setPhone("+55555555555");
        return newAdmin;
    }

    private static String generateUserName() {
        String time = String.valueOf(System.nanoTime());
        return "Donald" + time + " " + "Trump" + time;
    }
}
