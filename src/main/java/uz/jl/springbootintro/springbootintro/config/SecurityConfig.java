package uz.jl.springbootintro.springbootintro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/profile")
                    .authenticated()
                .requestMatchers("/manager")
                .hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/admin")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();

        http.formLogin();

        return http.build();
    }

}
