package br.com.laboratorioce.termometro.conteudo;

import br.com.laboratorioce.termometro.conteudo.dto.ConteudoPublicoDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/conteudos")
public class ConteudoController {

    private final ConteudoService service;

    public ConteudoController(ConteudoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ConteudoPublicoDTO> listar(@RequestParam(required = false) String categoria) {
        return service.listarPublicados(categoria);
    }

    @GetMapping("/{slug}")
    public ConteudoPublicoDTO buscar(@PathVariable String slug) {
        return service.buscarPublicado(slug);
    }
}
