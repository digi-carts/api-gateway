package com.digicart.gateway;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Needs JWT_SECRET; covered by JwtAuthFilterTest and Cucumber")
@SpringBootTest
class ApiGatewayApplicationTests {
    @Test
    void contextLoads() {}
}
