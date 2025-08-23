package com.project.apigateway.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.List;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                // Use the lambda-based configuration for CSRF
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/users/register", "/api/users/login").permitAll()
                        .anyExchange().authenticated())
                // The .and() method is also being phased out in favor of chaining directly
                .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    public AuthenticationWebFilter authenticationWebFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(
                new ReactiveAuthenticationManager() {
                    @Override
                    public Mono<Authentication> authenticate(Authentication authentication) {
                        String authToken = authentication.getCredentials().toString();
                        try {
                            SecretKey signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
                            Claims claims = Jwts.parserBuilder()
                                    .setSigningKey(signingKey)
                                    .build()
                                    .parseClaimsJws(authToken)
                                    .getBody();

                            String username = claims.getSubject();
                            String role = claims.get("role", String.class);
                            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                            return Mono.just(auth);

                        } catch (Exception e) {
                            return Mono.empty();
                        }
                    }
                });

        authenticationWebFilter.setServerAuthenticationConverter(exchange -> {
            List<String> authHeaders = exchange.getRequest().getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION);
            if (authHeaders.isEmpty() || !authHeaders.get(0).startsWith("Bearer ")) {
                return Mono.empty();
            }
            String authToken = authHeaders.get(0).substring(7);
            return Mono.just(new UsernamePasswordAuthenticationToken(null, authToken));
        });

        authenticationWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/api/**"));

        return authenticationWebFilter;
    }
}