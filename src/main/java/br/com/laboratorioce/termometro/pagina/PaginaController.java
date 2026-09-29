package br.com.laboratorioce.termometro.pagina;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/paginas")
public class PaginaController {

    private final PaginaRepository repository;

    public PaginaController(PaginaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{chave}")
    public PaginaDTO buscar(@PathVariable String chave) {
        return repository.findById(chave).map(PaginaDTO::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
