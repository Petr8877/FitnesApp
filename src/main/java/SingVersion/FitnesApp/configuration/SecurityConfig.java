package SingVersion.FitnesApp.configuration;

import SingVersion.FitnesApp.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers("/users/registration").permitAll()
                                .requestMatchers("/users/verification").permitAll()
                                .requestMatchers("/users/login").permitAll()
                                .requestMatchers("/users/me").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/users/**").hasAuthority("ADMIN")
                                .requestMatchers("/users").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/product").permitAll()
                                .requestMatchers(HttpMethod.POST, "/product").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/product/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/recipe").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/recipe/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/audit").hasAuthority("ADMIN")
                                .requestMatchers("/audit/**").hasAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
