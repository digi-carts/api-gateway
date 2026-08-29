Feature: API gateway JWT component

  Scenario: protected routes reject missing bearer tokens
    When I GET "/api/v1/orders" without a token
    Then the response status is 401

  Scenario: health is public
    When I GET "/health" without a token
    Then the response status is 200
