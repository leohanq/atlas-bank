package com.atlas.bank.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                    // Accounts
                    .requestMatchers(HttpMethod.POST, "/api/v1/accounts").hasAuthority("ROL_ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/v1/accounts").hasAuthority("ROL_ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/v1/accounts/{id}").hasAnyAuthority("ROL_USER", "ROL_ADMIN")
                    // Transactions
                    .requestMatchers(HttpMethod.POST, "/api/v1/transactions/transfer").hasAnyAuthority("ROL_USER", "ROL_ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/v1/transactions/{id}/transactions").hasAnyAuthority("ROL_USER", "ROL_ADMIN")
                    // h2
                    .requestMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()
                ).oauth2ResourceServer(oauth -> oauth.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
                ).headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                ).csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var realAuthorities = jwt.getClaimAsMap("realm_access");
            if (realAuthorities == null) {
                return List.of();
            }

            var roles = (List<String>) realAuthorities.get("roles");
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });
        return jwtAuthenticationConverter;
    }
}