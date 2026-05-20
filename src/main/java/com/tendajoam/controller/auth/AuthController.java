package com.tendajoam.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.users.Usuari;
import com.tendajoam.security.JwtUtil;
import com.tendajoam.service.interfaces.UsuariService;

import com.tendajoam.security.LoginRequest;
import com.tendajoam.security.LoginResponse;
import com.tendajoam.security.RegisterRequest;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuariService usuariService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UsuariService usuariService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuariService = usuariService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        System.out.println("LOGIN EMAIL = " + loginRequest.getEmail());
        System.out.println("LOGIN PASS  = " + loginRequest.getPass());

        Usuari usuari = usuariService.findByEmail(loginRequest.getEmail());
        System.out.println("DB USER     = " + usuari);

        if (usuari == null) {
            System.out.println("-> 403: Usuari no trobat");
            return ResponseEntity.status(403).body("Usuari no trobat");
        }

        System.out.println("DB HASH     = " + usuari.getPass());
        System.out.println("MATCH?      = " + passwordEncoder.matches(loginRequest.getPass(), usuari.getPass()));

        if (!passwordEncoder.matches(loginRequest.getPass(), usuari.getPass())) {
            System.out.println("-> 403: Contrasenya incorrecta");
            return ResponseEntity.status(403).body("Contrasenya incorrecta");
        }

        String token = jwtUtil.generateToken(usuari);

        return ResponseEntity.ok(
                new LoginResponse(
                        token,
                        usuari.getNom(),
                        usuari.getRol().name()
                )
        );
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

        if (usuariService.findByEmail(req.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Aquest email ja està registrat");
        }

        Usuari usuari = new Usuari();
        usuari.setIdUsuari(req.getIdUsuari());
        usuari.setNom(req.getNom());
        usuari.setEmail(req.getEmail());
        usuari.setPass(passwordEncoder.encode(req.getPass()));
        usuari.setRol(req.getRol());

        usuariService.save(usuari);

        return ResponseEntity.ok("Usuari registrat correctament");
    }

}
