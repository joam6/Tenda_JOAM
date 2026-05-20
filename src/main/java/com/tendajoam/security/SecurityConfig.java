package com.tendajoam.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	// 🔐 ROLS
	private static final String ADMIN = "ADMIN";
	private static final String VENEDOR = "VENEDOR";
	private static final String CLIENT = "CLIENT";

	// 🔓 RUTES PÚBLIQUES
	private static final String LOGIN_URL = "/api/auth/login";
	private static final String REGISTER_URL = "/api/auth/register";
	private static final String AUTH_URL = "/api/auth/**";
	private static final String PRODUCTES_URL = "/api/productes/**";

	// 🔐 RUTES PRIVADES
	private static final String ADMIN_URL = "/api/admins/**";
	private static final String VENEDOR_URL = "/api/venedors/**";
	private static final String CLIENT_URL = "/api/clientes/**";

	// 🔓 RECURSOS ESTÀTICS
	private static final String[] SWAGGER_RESOURCES = { "/swagger-ui/**", "/v3/api-docs/**" };
	private static final String[] STATIC_RESOURCES = { "/favicon.ico", "/index.html", "/login.html", "/**/*.html",
			"/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif", "/**/*.svg", "/**/*.ico", "/","/main" };

	private final JwtFilter jwtFilter;
	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(JwtFilter jwtFilter, CustomUserDetailsService userDetailsService) {
		this.jwtFilter = jwtFilter;
		this.userDetailsService = userDetailsService;
	}

	// 🔐 NECESSARI PER AUTENTICAR USUARIS
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



	// 🔐 AuthenticationManager (OBLIGATORI PER AL LOGIN)
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// 🔐 CADENA DE SEGURETAT
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				        .authorizeHttpRequests(auth -> auth

						// 🔓 RUTES PÚBLIQUES
		        		
						.requestMatchers(LOGIN_URL).permitAll().requestMatchers(REGISTER_URL).permitAll()
						.requestMatchers(AUTH_URL).permitAll().requestMatchers(PRODUCTES_URL).permitAll()
						.requestMatchers(STATIC_RESOURCES).permitAll().requestMatchers(SWAGGER_RESOURCES).permitAll()

	
						// 🔐 RUTES AMB ROLS
						.requestMatchers(ADMIN_URL).hasRole(ADMIN).requestMatchers(VENEDOR_URL).hasRole(VENEDOR)
						.requestMatchers(CLIENT_URL).hasRole(CLIENT)

						// 🔐 RESTA D’ENDPOINTS
						.anyRequest().authenticated());

		
		

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
