package com.digicart.gateway.cucumber;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

public class GatewayStepDefinitions {

    @Autowired
    private WebTestClient webTestClient;

    private int lastStatus;

    @When("I GET {string} without a token")
    public void getWithoutToken(String path) {
        FluxExchangeResult<Void> result = webTestClient.get()
                .uri(path)
                .exchange()
                .returnResult(Void.class);
        lastStatus = result.getStatus().value();
    }

    @Then("the response status is {int}")
    public void statusIs(Integer expected) {
        if (lastStatus != expected) {
            throw new AssertionError("expected " + expected + " but was " + lastStatus);
        }
    }
}
