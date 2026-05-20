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

		System.out.println("REQUEST PATH = " + request.getRequestURI());
		System.out.println("AUTH HEADER = " + request.getHeader("Authorization"));


		
		String path = request.getRequestURI();

		if (path.startsWith("/api/auth")
			|| path.startsWith("/api/productes")
		    || path.startsWith("/swagger-ui")
		    || path.startsWith("/v3/api-docs")
		    || path.endsWith(".html")
		    || path.endsWith(".css")
		    || path.endsWith(".js")
		    || path.endsWith(".png")
		    || path.endsWith(".jpg")
		    || path.endsWith(".jpeg")
		    || path.endsWith(".gif")
		    || path.endsWith(".svg")
		    || path.endsWith(".ico")
		    || path.equals("/")
		    || path.equals("/main")
		    || path.startsWith("/debug")
		    

) 
		{

		    chain.doFilter(request, response);
		    return;
		}


	    // A PARTIR D'AQUI, VALIDAR TOKEN
	    String authHeader = request.getHeader("Authorization");

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {

	        String token = authHeader.substring(7);

	        if (jwtUtil.validateToken(token)) {

	            String email = jwtUtil.extractEmail(token);
	            Usuari usuari = usuariService.findByEmail(email);

	            CustomUserDetails userDetails = new CustomUserDetails(usuari);

	            UsernamePasswordAuthenticationToken authToken =
	                    new UsernamePasswordAuthenticationToken(
	                            userDetails, null, userDetails.getAuthorities());

	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	            SecurityContextHolder.getContext().setAuthentication(authToken);
	        }
	    }

	    chain.doFilter(request, response);
	}


}
