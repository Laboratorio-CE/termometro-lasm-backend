package br.com.laboratorioce.termometro.pagina;

import br.com.laboratorioce.termometro.admin.AdminUserRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/admin/paginas")
public class PaginaAdminController {

    private final PaginaRepository repository;
    private final AdminUserRepository adminRepository;

    public PaginaAdminController(PaginaRepository repository, AdminUserRepository adminRepository) {
        this.repository = repository;
        this.adminRepository = adminRepository;
    }

    @GetMapping
    public List<PaginaDTO> listar() {
        return repository.findAll(Sort.by("chave")).stream().map(PaginaDTO::from).toList();
    }

    @GetMapping("/{chave}")
    public PaginaDTO buscar(@PathVariable String chave) {
        return PaginaDTO.from(encontrar(chave));
    }

    @PutMapping("/{chave}")
    @Transactional
    public PaginaDTO atualizar(@PathVariable String chave, @RequestBody @Valid PaginaForm form, Authentication auth) {
        var pagina = encontrar(chave);
        pagina.atualizar(form.titulo(), form.corpo(), adminRepository.findByEmail(auth.getName()).orElseThrow());
        return PaginaDTO.from(pagina);
    }

    private Pagina encontrar(String chave) {
        return repository.findById(chave).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
