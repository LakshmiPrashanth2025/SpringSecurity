package com.example.SpringSecurity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.SpringSecurity.filter.JWTFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private AuthenticationProvider authProvider;
	
	@Autowired
	private JWTFilter jwtFilter;
	
	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        http
	            // 🔥 Disable CSRF (required for stateless JWT APIs)
	            .csrf(csrf -> csrf.disable())

	            // 🔥 Stateless session (no session, only token)
	            .sessionManagement(session -> 
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )
//	            .httpBasic(Customizer.withDefaults())
//				.formLogin(Customizer.withDefaults())
//				.logout(logout -> logout
//						.logoutUrl("/logout")
//						.logoutSuccessUrl("/register")
//						)

	            // 🔥 Authorization rules
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers(
	                            "/register",
	                            "/login",
	                            "/swagger-ui/**",
	                            "/v3/api-docs/**"
	                    ).permitAll()
	                    .anyRequest().authenticated()
	            )

	            // 🔥 Custom auth provider
	            .authenticationProvider(authProvider)

	            // 🔥 JWT filter BEFORE username/password filter
	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	            .csrf(csrf -> csrf.disable());

	        return http.build();
	  
	}
	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//	
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("tech-amplifers")
//				.password("P@ssw0rd")
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user);
//	}

}
