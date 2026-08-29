package com.digicart.gateway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class JwtAuthFilterTest {

    private static final String SECRET = "01234567890123456789012345678901";

    private final JwtAuthFilter filter = new JwtAuthFilter();

    @BeforeEach
    void setSecret() {
        ReflectionTestUtils.setField(filter, "jwtSecret", SECRET);
    }

    @Test
    void protectedPathWithoutTokenIsUnauthorized() {
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/api/orders").build());
        filter.filter(exchange, ignored -> Mono.empty()).block();
        assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void publicLoginSkipsJwt() {
        AtomicBoolean chained = new AtomicBoolean(false);
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/api/v1/auth/login").build());
        filter.filter(exchange, ex -> {
            chained.set(true);
            return Mono.empty();
        }).block();
        assertThat(chained).isTrue();
        assertThat(exchange.getResponse().getStatusCode()).isNull();
    }

    @Test
    void validJwtInjectsUserHeaders() {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                .subject("user-99")
                .claim("role", "merchant")
                .signWith(key)
                .compact();
        AtomicReference<ServerWebExchange> forwarded = new AtomicReference<>();
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/orders")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build());
        GatewayFilterChain chain = ex -> {
            forwarded.set(ex);
            return Mono.empty();
        };
        filter.filter(exchange, chain).block();
        assertThat(forwarded.get().getRequest().getHeaders().getFirst("X-User-Id")).isEqualTo("user-99");
        assertThat(forwarded.get().getRequest().getHeaders().getFirst("X-User-Role")).isEqualTo("merchant");
    }

    @Test
    void invalidJwtIsUnauthorized() {
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/orders")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer not-a-jwt")
                        .build());
        filter.filter(exchange, ignored -> Mono.empty()).block();
        assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void storefrontPathIsPublic() {
        AtomicBoolean chained = new AtomicBoolean(false);
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/v1/storefront/resolve").build());
        filter.filter(exchange, ex -> {
            chained.set(true);
            return Mono.empty();
        }).block();
        assertThat(chained).isTrue();
    }

    @Test
    void healthPathIsPublic() {
        AtomicBoolean chained = new AtomicBoolean(false);
        ServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/health").build());
        filter.filter(exchange, ex -> {
            chained.set(true);
            return Mono.empty();
        }).block();
        assertThat(chained).isTrue();
    }
}
