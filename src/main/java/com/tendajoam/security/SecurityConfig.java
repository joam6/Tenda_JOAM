
package com.tendajoam.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
	private static final String PERFIL_USUARI_URL = "/api/usuaris/perfil/**";

	// 🔓 RECURSOS ESTÀTICS
	private static final String[] SWAGGER_RESOURCES = { "/swagger-ui/**", "/v3/api-docs/**" };
	private static final String[] STATIC_RESOURCES = { "/favicon.ico", "/index.html", "/login.html", "/*.html",
			"/login", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif",
			"/**/*.svg", "/**/*.ico", "/", "/main" };

	private final JwtFilter jwtFilter;

	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
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
		http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// 1. Rutes Públiques (PRIMER)
						.requestMatchers(HttpMethod.POST, "/api/productes/afegir").permitAll()
						.requestMatchers(STATIC_RESOURCES).permitAll()
					    .requestMatchers(SWAGGER_RESOURCES).permitAll()
					    .requestMatchers("/api/auth/**").permitAll() // Traiem productes d'aquí
					    .requestMatchers("/api/usuaris/perfil/**").permitAll()
					    .requestMatchers("/favicon.ico", "/css/**", "/js/**", "/img/**").permitAll()

					    // 2. Rutes de Productes (GET obert, POST/PUT/DELETE protegit)
					    .requestMatchers(HttpMethod.GET, "/api/productes/**").permitAll()
					    .requestMatchers(HttpMethod.POST, "/api/productes/**").hasAnyRole("VENEDOR", "ADMIN")
					    .requestMatchers(HttpMethod.PUT, "/api/productes/**").hasAnyRole("VENEDOR", "ADMIN")
					    .requestMatchers(HttpMethod.DELETE, "/api/productes/**").hasAnyRole("VENEDOR", "ADMIN")

					    // 3. Rutes de Carro
					    .requestMatchers(HttpMethod.DELETE, "/api/carro/*/buidar").hasRole("CLIENT")
					    .requestMatchers(HttpMethod.DELETE, "/api/carro/*/eliminar/*").hasRole("CLIENT")
					    .requestMatchers("/api/carro/**").hasRole("CLIENT")
						
						.requestMatchers(HttpMethod.POST, "/api/productes/**").hasAnyRole("VENEDOR", "ADMIN")

						.requestMatchers(ADMIN_URL).hasRole("ADMIN").requestMatchers(VENEDOR_URL).hasRole("VENEDOR")
						.requestMatchers(CLIENT_URL).hasRole("CLIENT")

						// 4. Qualsevol altra ruta requereix autenticació
						.anyRequest().authenticated())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowedOrigins(List.of("*"));
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    // AFEGEIX AIXÒ PER SEGURETAT:
	    config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
	    config.setAllowCredentials(false); 
	    
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}
}