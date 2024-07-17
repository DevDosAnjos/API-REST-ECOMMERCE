package com.app.labdesoftware.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/product/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/product/brand/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/product/category/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/product/inStock").permitAll()
                        .requestMatchers(HttpMethod.GET, "/category/all").permitAll()
                        //ADICIONAR OS ENDPOINTS DE DELETE EM CART/ORDER

                        //Auth
                        .requestMatchers(HttpMethod.POST,"/admin/**").hasRole("ADMIN")
                        //Product
                        .requestMatchers(HttpMethod.GET, "/product/outOfStock").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/product").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/product").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/product").hasRole("ADMIN")
                        //Category
                        .requestMatchers(HttpMethod.GET, "/category/disable").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/category").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/category").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/category").hasRole("ADMIN")
                        //Order
                        .requestMatchers(HttpMethod.GET,"/orders/all").hasRole("ADMIN")
                        //Cart
                        .requestMatchers(HttpMethod.GET,"/cart/all").hasRole("ADMIN")
                        //Purchase
                        .requestMatchers(HttpMethod.GET,"/purchase/all").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
