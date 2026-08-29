package com.digicart.gateway.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(properties = {
        "JWT_SECRET=01234567890123456789012345678901",
        "AUTH_SERVICE_URL=http://127.0.0.1:65535",
        "PLATFORM_SERVICE_URL=http://127.0.0.1:65535",
        "NOTIFICATION_SERVICE_URL=http://127.0.0.1:65535",
        "CATALOG_SERVICE_URL=http://127.0.0.1:65535",
        "ORDER_SERVICE_URL=http://127.0.0.1:65535",
        "PAYMENT_SERVICE_URL=http://127.0.0.1:65535",
        "SHIPPING_SERVICE_URL=http://127.0.0.1:65535",
        "STORE_SERVICE_URL=http://127.0.0.1:65535",
        "STOREFRONT_SERVICE_URL=http://127.0.0.1:65535",
        "OFFER_SERVICE_URL=http://127.0.0.1:65535",
        "BILLING_SERVICE_URL=http://127.0.0.1:65535",
        "AUDIT_LOG_SERVICE_URL=http://127.0.0.1:65535"
})
public class CucumberSpringConfiguration {
}
