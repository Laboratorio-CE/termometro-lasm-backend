package br.com.laboratorioce.termometro.conteudo;

import br.com.laboratorioce.termometro.conteudo.dto.ConteudoDetalheDTO;
import br.com.laboratorioce.termometro.conteudo.dto.ConteudoForm;
import br.com.laboratorioce.termometro.conteudo.dto.ConteudoResumoDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/conteudos")
public class ConteudoAdminController {

    private final ConteudoService service;

    public ConteudoAdminController(ConteudoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ConteudoResumoDTO> listar(@RequestParam(required = false) String status,
            @RequestParam(required = false) String categoria) {
        return service.listar(status, categoria);
    }

    @GetMapping("/{id}")
    public ConteudoDetalheDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConteudoDetalheDTO criar(@RequestBody @Valid ConteudoForm form, Authentication auth) {
        return service.criar(form, auth.getName());
    }

    @PutMapping("/{id}")
    public ConteudoDetalheDTO atualizar(@PathVariable Long id, @RequestBody @Valid ConteudoForm form,
            Authentication auth) {
        return service.atualizar(id, form, auth.getName());
    }

    @PostMapping("/{id}/publicar")
    public ConteudoDetalheDTO publicar(@PathVariable Long id, Authentication auth) {
        return service.publicar(id, auth.getName());
    }

    @PostMapping("/{id}/despublicar")
    public ConteudoDetalheDTO despublicar(@PathVariable Long id, Authentication auth) {
        return service.despublicar(id, auth.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
