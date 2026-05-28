package com.tendajoam.security;

import java.io.IOException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.tendajoam.entity.users.Usuari;
import com.tendajoam.service.interfaces.UsuariService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UsuariService usuariService;

	public JwtFilter(JwtUtil jwtUtil, UsuariService usuariService) {
		this.jwtUtil = jwtUtil;
		this.usuariService = usuariService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		if (path.startsWith("/img/") || path.startsWith("/css/") || path.startsWith("/js/")) {
			chain.doFilter(request, response);
			return;
		}
		
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
				if (jwtUtil.validateToken(token)) {
					String email = jwtUtil.extractEmail(token);
					if (SecurityContextHolder.getContext().getAuthentication() == null) {
						Usuari usuari = usuariService.findByEmail(email);
						if (usuari != null) {
							CustomUserDetails userDetails = new CustomUserDetails(usuari);

							System.out.println(
									"Usuari autenticat: " + email + " amb autoritats: " + userDetails.getAuthorities());

							UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());
							authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							SecurityContextHolder.getContext().setAuthentication(authToken);
						}
					}
				}
			} catch (Exception e) {
				SecurityContextHolder.clearContext();
			}
		}
		chain.doFilter(request, response);
	}
}