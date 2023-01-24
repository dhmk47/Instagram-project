package com.project.instagram.config;

import com.project.instagram.config.auth.CustomFailureHandler;
import com.project.instagram.service.auth.OAuth2DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final OAuth2DetailService oAuth2DetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .csrf()
                .disable();


        http.authorizeRequests()
                .antMatchers("/sign-in", "/sign-up")
                .permitAll()
                .antMatchers("/", "/{userNickname}", "/{userNickname}/saved", "/{userNickname}/tagged")
                .authenticated()

                .and()

                .formLogin()
                .loginPage("/sign-in")
                .loginProcessingUrl("/auth/login")
                .failureHandler(new CustomFailureHandler())
                .defaultSuccessUrl("/")

                .and()

                .logout()
                .logoutSuccessUrl("/sign-in")

                .and()

                .oauth2Login()
                .userInfoEndpoint()
                .userService(oAuth2DetailService);
    }
}