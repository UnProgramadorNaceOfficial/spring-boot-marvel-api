package com.api.marvel.config;

import com.api.marvel.config.filter.JwtTokenValidator;
import com.api.marvel.service.impl.UserDetailServiceImpl;
import com.api.marvel.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    @Qualifier("deleagtedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

//    @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(request -> {
//                    /* Define the public endpoints */
//                    request.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
//
//                    /* Define the private endpoints */
//                    request.requestMatchers(HttpMethod.GET, "/character/find").hasAnyRole("ADMIN", "USER");
//                    request.requestMatchers(HttpMethod.GET, "/character/find/{characterId}").hasRole("ADMIN");
//
//                    request.requestMatchers(HttpMethod.GET, "/comic/find").hasAuthority("READ_COMIC");
//                    request.requestMatchers(HttpMethod.GET, "/comic/findAll").hasAuthority("READ_COMIC");
//                    request.requestMatchers(HttpMethod.GET, "/comic/find/{comicId}").hasAuthority("READ_COMIC");
//
//                    /* The rest of endpoints */
//                    request.anyRequest().denyAll();
//                })
//                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
//                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class);
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
