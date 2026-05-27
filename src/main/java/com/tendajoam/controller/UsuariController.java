package com.tendajoam.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tendajoam.entity.users.PerfilRequest;
import com.tendajoam.entity.users.Usuari;
import com.tendajoam.service.interfaces.UsuariService;

@RestController
@RequestMapping("/api/usuaris")
@CrossOrigin(origins = "*") 
public class UsuariController {

	private final UsuariService usuariService;

	public UsuariController(UsuariService usuariService) {
		this.usuariService = usuariService;
	}

	@GetMapping
	public ResponseEntity<List<Usuari>> getAll() {
		return ResponseEntity.ok(usuariService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuari> getById(@PathVariable String id) {
		return usuariService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	

	@PutMapping("/perfil/{id}")
	@CrossOrigin(origins = "*")
	// 👈 canviem ResponseEntity<Usuari> o similar per ResponseEntity<?>
	public ResponseEntity<?> updatePerfil(@PathVariable String id, @RequestBody PerfilRequest body) {
	    
	    return usuariService.findById(id).<ResponseEntity<?>>map(usuari -> {
	        
	        if (body.getNom() != null && !body.getNom().trim().isEmpty()) {
	            usuari.setNom(body.getNom().trim());
	        }
	        
	        if (body.getDireccio() != null) {
	            usuari.setDireccio(body.getDireccio().trim());
	        }

	        Usuari guardat = usuariService.save(usuari);
	        return ResponseEntity.ok(guardat); // Retorna ResponseEntity<Usuari>
	        
	    }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuari no trobat amb ID: " + id)); 
	}

	@PostMapping
	public ResponseEntity<Usuari> create(@RequestBody Usuari usuari) {
		return ResponseEntity.ok(usuariService.save(usuari));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuari> update(@PathVariable String id, @RequestBody Usuari usuari) {
		usuari.setIdUsuari(id);
		return ResponseEntity.ok(usuariService.save(usuari));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		usuariService.delete(id);
		return ResponseEntity.noContent().build();
	}
}