package com.ergasia.spring.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ergasia.spring.login.security.jwt.AuthEntryPointJwt;
import com.ergasia.spring.login.security.jwt.AuthTokenFilter;
import com.ergasia.spring.login.security.services.UserDetailsServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
@CrossOrigin(origins = "http://localhost:4200")

public class WebSecurityConfig {
  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());

      return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

@Bean
public CorsConfigurationSource corsConfigurationSource() {
  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  CorsConfiguration corsConfiguration = new CorsConfiguration();
  corsConfiguration.applyPermitDefaultValues();
  corsConfiguration.setAllowedMethods(
          Arrays.asList(
                  HttpMethod.GET.name(), HttpMethod.HEAD.name(),
                  HttpMethod.POST.name(), HttpMethod.PUT.name(),
                  HttpMethod.DELETE.name())
  );
  corsConfiguration.setAllowCredentials(true);
  corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
  source.registerCorsConfiguration("/**", corsConfiguration);
  return source;
}
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth ->
          auth.requestMatchers("/api/auth/**").permitAll()
              .requestMatchers("/api/test/**").permitAll()
                  .requestMatchers("/error/**").permitAll()
                  .requestMatchers("/api/account/accounts_list/create").permitAll()
                  .requestMatchers("/api/account/accounts_list/find_user/**").permitAll()
                  .anyRequest().authenticated()
        ).cors()
            .configurationSource(corsConfigurationSource())
            .and();

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
