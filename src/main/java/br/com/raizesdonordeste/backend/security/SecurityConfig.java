package br.com.raizesdonordeste.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilterServletRegistration(
            JwtAuthenticationFilter filter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registration =
                new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/cadastro", "/auth/login").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/smoke").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/produtos", "/produtos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/unidades", "/unidades/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/produtos").hasAnyRole("ATENDENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/produtos/**").hasAnyRole("ATENDENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/unidades").hasAnyRole("ATENDENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/unidades/**").hasAnyRole("ATENDENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/unidades/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/pagamentos").hasAnyRole("CLIENTE", "ATENDENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/pagamentos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/pedidos/**").hasAnyRole("CLIENTE", "ATENDENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/pedidos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/estoques").hasAnyRole("ATENDENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/estoques/**").hasAnyRole("ATENDENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/estoques/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/auditorias", "/auditorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/auditorias").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpStatus.FORBIDDEN.value())))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}