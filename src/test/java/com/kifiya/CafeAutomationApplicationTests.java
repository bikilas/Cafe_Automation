package com.kifiya;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// @SpringBootTest
// class CafeAutomationApplicationTests {

//     @Test
//     void contextLoads() {
//         // This test will verify that the Spring application context loads successfully
//     }
// }
@SpringBootTest
@ActiveProfiles("test")
class CafeAutomationApplicationTests {
    @Test
    void contextLoads() {
    }
}