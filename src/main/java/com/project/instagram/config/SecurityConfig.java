package com.project.instagram.config;

import com.project.instagram.config.auth.CustomFailureHandler;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .csrf()
                .disable();


        http.authorizeRequests()
                .anyRequest()
                .permitAll()

                .and()

                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/auth/login")
                .failureHandler(new CustomFailureHandler())
                .defaultSuccessUrl("/main")

                .and()

                .logout()
                .logoutSuccessUrl("/main");
    }
}