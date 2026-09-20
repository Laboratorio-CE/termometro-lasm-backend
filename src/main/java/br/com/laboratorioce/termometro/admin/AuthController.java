package br.com.laboratorioce.termometro.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    public record UsuarioDTO(String nome, String email, String papel) {

    }

    private final AdminUserRepository repository;

    public AuthController(AdminUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping ("/eu")
    public ResponseEntity<UsuarioDTO> eu(Authentication auth, CsrfToken csrfToken) {
        csrfToken.getToken();
        
        if (auth == null) {
            return ResponseEntity.status(401).build();
        }
        return repository.findByEmail(auth.getName())
        .map(admin -> ResponseEntity.ok(new UsuarioDTO(admin.getNome(), admin.getEmail(), admin.getPapel())))
        .orElseGet(() -> ResponseEntity.status(401).build());
    }
}


