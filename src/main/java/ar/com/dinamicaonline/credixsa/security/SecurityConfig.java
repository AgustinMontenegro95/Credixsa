package ar.com.dinamicaonline.credixsa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/cash_withdrawal")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/transfer_money")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/deposit_money")).permitAll()
                        .anyRequest().authenticated())
                .httpBasic();
        return http.build();
    }

}