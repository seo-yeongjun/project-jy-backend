package com.projectjy.projectjybackend.config;

import com.projectjy.projectjybackend.security.*;
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
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CheckMemberInterceptor checkMemberInterceptor;


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

                .and().authorizeRequests().antMatchers("**","*","/**").permitAll()
                .and().authorizeRequests().antMatchers("/sale/book/**","/sale/complete/**","/sale/history/**","/member/**","/sale/book/delete/**","/sale/book/edit/**","/sale/book/edit").authenticated()
                .and().apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(checkMemberInterceptor).addPathPatterns("/sale/book/**","/sale/complete/**","/sale/history/**","/member/**","/sale/book/delete/**","/sale/book/edit/**","/sale/book/edit");
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(false); // 쿠키를 받을건지
        configuration.setAllowedOrigins(Arrays.asList("https://skhubook.store","http://localhost","https://skhubook.store","http://3.37.76.20:8000","https://3.37.76.20:8000","http://skhubook.store:8000","https://skhubook.store:8000","https://skhubook.store:443")); // 허용할 주소
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}


