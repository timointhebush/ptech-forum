package com.example.ptechforum.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SecurityHandler securityHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                    .csrf()
                .and()
                    .authorizeRequests()
                    .antMatchers("/posts/new/**", "/posts/**/edit").authenticated()
                    .anyRequest()
                    .permitAll()
                .and()
                    .formLogin()
                    .loginPage("/member/login")
                    .usernameParameter("email")
                    .permitAll()
                    .successHandler(securityHandler)
                    .defaultSuccessUrl("/")
                    .failureUrl("/member/login?error=1")
                    .loginProcessingUrl("/member/authenticate")
                .and()
                    .logout()
                    .logoutUrl("/member/logout")
                    .permitAll()
                    .logoutSuccessUrl("/");
    }
}
