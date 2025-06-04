package com.code.SellCar_Spring.configurations;

import java.util.List;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.code.SellCar_Spring.enums.UserRole;
import com.code.SellCar_Spring.services.jwt.UserService;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor

public class WebSecurityConfiguration {
	private final UserService userService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter; 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Enable global CORS
        .csrf(csrf -> csrf.disable())
		
			.authorizeHttpRequests(request -> request
					.requestMatchers("/api/auth/**").permitAll()
					.requestMatchers("/api/customer/**").hasAnyAuthority(UserRole.CUSTOMER.name())
					.requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.name())
					.anyRequest().authenticated())
			.sessionManagement(manager -> manager
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
		
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService.userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
		
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOrigins(List.of("http://localhost:4200")); // ✅ Angular frontend
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        config.setAllowedHeaders(List.of("*"));
	        config.setAllowCredentials(true); // ✅ For sending Authorization header (JWT)

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config); // apply CORS to all endpoints
	        return source;
	    }
	
}
