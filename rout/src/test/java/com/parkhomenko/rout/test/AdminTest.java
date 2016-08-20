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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Dmytro Parkhomenko
 *         Created on 20.08.16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("development")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class AdminTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void get_all_admins() {
        ResponseEntity<Admin[]> responseEntity = restTemplate.getForEntity("/admins/all", Admin[].class);
        List<Admin> admins = Arrays.asList(responseEntity.getBody());
        System.out.println("|******************get_all_admins: print data: *************************************|");
        admins.forEach(System.out::println);
        System.out.println("|***********************************************************************************|");
        assertTrue("Status is not OK", responseEntity.getStatusCode().is2xxSuccessful());
    }
}
