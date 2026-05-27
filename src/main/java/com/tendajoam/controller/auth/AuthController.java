package com.tendajoam.controller.auth;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.users.Usuari;
import com.tendajoam.entity.users.Cliente;
import com.tendajoam.entity.users.Venedor;
import com.tendajoam.entity.users.VerificationToken;

import com.tendajoam.repository.VerificationTokenRepository;
import com.tendajoam.repository.VenedorRepository;
import com.tendajoam.service.interfaces.EmailService;
import com.tendajoam.service.interfaces.UsuariService;
import com.tendajoam.service.interfaces.ClienteService;

import com.tendajoam.security.JwtUtil;
import com.tendajoam.security.LoginRequest;
import com.tendajoam.security.LoginResponse;
import com.tendajoam.security.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UsuariService usuariService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final VerificationTokenRepository verificationTokenRepository;
	private final EmailService emailService;

	private final ClienteService clienteService;
	private final VenedorRepository vendedorRepository;

	public AuthController(UsuariService usuariService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			VerificationTokenRepository verificationTokenRepository, EmailService emailService,
			ClienteService clienteService, VenedorRepository vendedorRepository) {
		this.usuariService = usuariService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.verificationTokenRepository = verificationTokenRepository;
		this.emailService = emailService;
		this.clienteService = clienteService;
		this.vendedorRepository = vendedorRepository;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Usuari usuari = usuariService.findByEmail(loginRequest.getEmail());

		if (usuari == null || !passwordEncoder.matches(loginRequest.getPass(), usuari.getPass())) {
			return ResponseEntity.status(403).body("Credencials incorrectes");
		}

		if (!usuari.isActiu()) {
			return ResponseEntity.status(403).body("Compte no confirmat. Revisa el teu correu.");
		}

		String token = jwtUtil.generateToken(usuari);
		// A AuthController.java
		return ResponseEntity.ok(new LoginResponse(token, usuari.getNom(), usuari.getRol().name(), usuari.getIdUsuari(),
				usuari.getEmail(),usuari.getDireccio() 
		));
	}

	@PostMapping("/register")
	@Transactional
	public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

		if (usuariService.findByEmail(req.getEmail()) != null) {
			return ResponseEntity.badRequest().body("Aquest email ja està registrat");
		}

		String rolNom = (req.getRol() != null) ? req.getRol().name().toUpperCase() : "CLIENT";
		String novaIdGenerada = "";

		// 1. Generació d'ID
		if ("VENDEDOR".equals(rolNom) || "VENEDOR".equals(rolNom)) {
			long totalVenedors = vendedorRepository.count();
			novaIdGenerada = "ven" + String.format("%02d", totalVenedors + 1);
		} else {
			long totalClients = clienteService.findAll().size();
			novaIdGenerada = "cli" + String.format("%02d", totalClients + 1);
		}

		// 2. Creació de l'entitat filla (Polimorfisme)
		Usuari nouUsuari;

		if ("VENDEDOR".equals(rolNom) || "VENEDOR".equals(rolNom)) {
			Venedor v = new Venedor();
			v.setIdUsuari(novaIdGenerada);
			v.setNom(req.getNom());
			v.setEmail(req.getEmail());
			v.setPass(passwordEncoder.encode(req.getPass()));
			v.setRol(req.getRol());
			v.setActiu(false);
			nouUsuari = v;
		} else {
			Cliente c = new Cliente();
			c.setIdUsuari(novaIdGenerada);
			c.setNom(req.getNom());
			c.setEmail(req.getEmail());
			c.setPass(passwordEncoder.encode(req.getPass()));
			c.setRol(req.getRol());
			c.setActiu(false);
			c.setPuntos(0);
			nouUsuari = c;
		}

		// 3. Un sol save. Amb JOINED, això guarda a la taula pare i a la taula filla
		usuariService.save(nouUsuari);

		// 4. Generació del Token
		String token = UUID.randomUUID().toString();
		VerificationToken vt = new VerificationToken();
		vt.setToken(token);
		vt.setUsuari(nouUsuari);
		vt.setExpiryDate(LocalDateTime.now().plusHours(24));
		verificationTokenRepository.save(vt);

		// 5. Enviament del correu (Utilitzant nouUsuari en lloc d'usuari)
		try {
			if ("VENDEDOR".equals(rolNom) || "VENEDOR".equals(rolNom)) {
				emailService.notificarNouVenedorAdmin(nouUsuari.getNom(), nouUsuari.getEmail(), token);
				return ResponseEntity.ok("Sol·licitud de venedor registrada! L'administrador validarà el teu compte.");
			} else {
				emailService.enviarConfirmacio(nouUsuari.getEmail(), token);
				return ResponseEntity.ok("Usuari registrat! Revisa el teu correu per confirmar el compte.");
			}
		} catch (Exception e) {
			System.err.println("❌ ERROR ENVIANT CORREU: " + e.getMessage());
			return ResponseEntity.status(500)
					.body("Usuari guardat correctament, però ha fallat l'enviament de la notificació.");
		}
	}

	@GetMapping("/confirm")
	public ResponseEntity<String> confirmar(@RequestParam String token) {
		VerificationToken vt = verificationTokenRepository.findByToken(token);

		if (vt == null)
			return ResponseEntity.badRequest().body("Token invàlid");
		if (vt.getExpiryDate().isBefore(LocalDateTime.now()))
			return ResponseEntity.badRequest().body("Token caducat");

		Usuari u = vt.getUsuari();
		u.setActiu(true);
		usuariService.save(u);

		return ResponseEntity.ok("Compte confirmat correctament! Ja pots iniciar sessió.");
	}
}