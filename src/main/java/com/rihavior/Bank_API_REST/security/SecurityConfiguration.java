package com.rihavior.Bank_API_REST.security;

import com.rihavior.Bank_API_REST.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/get_accounts").hasAnyRole("HOLDER", "ADMIN")
                .mvcMatchers(HttpMethod.GET,"/get_account/{accountId}").hasAnyRole("HOLDER", "ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_checking").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_savings").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_credit_card").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_account_holder").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_third_party").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/transfer_funds").hasAnyRole("HOLDER", "ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/modify_balance").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/third_transfer").hasRole("THIRD")

                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();

    }

}
