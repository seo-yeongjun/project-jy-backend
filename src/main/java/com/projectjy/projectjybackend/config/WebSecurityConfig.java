package com.projectjy.projectjybackend.config;

import com.projectjy.projectjybackend.security.JwtAuthenticationEntryPoint;
import com.projectjy.projectjybackend.security.JwtSecurityConfig;
import com.projectjy.projectjybackend.security.TokenProvider;
import com.projectjy.projectjybackend.security.JwtAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler)

                .and().authorizeRequests().antMatchers("/auth/**").permitAll()
                .and().authorizeRequests().antMatchers("/info/**").permitAll()
                .anyRequest().authenticated()

                .and().apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(false); // 쿠키를 받을건지
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","192.168.10:3001","192.168.10:3002,","192.168.10:3000","192.168.10:3003"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));

        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}


