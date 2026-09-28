package br.com.laboratorioce.termometro.conteudo;

import br.com.laboratorioce.termometro.admin.AdminUser;
import br.com.laboratorioce.termometro.admin.AdminUserRepository;
import br.com.laboratorioce.termometro.conteudo.dto.ConteudoDetalheDTO;
import br.com.laboratorioce.termometro.conteudo.dto.ConteudoForm;
import br.com.laboratorioce.termometro.conteudo.dto.ConteudoResumoDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ConteudoService {

    private final ConteudoRepository repository;
    private final AdminUserRepository adminRepository;

    public ConteudoService(ConteudoRepository repository, AdminUserRepository adminRepository) {
        this.repository = repository;
        this.adminRepository = adminRepository;
    }

    @Transactional(readOnly = true)
    public List<ConteudoResumoDTO> listar(String status, String categoria) {
        return repository.listar(status, categoria).stream().map(ConteudoResumoDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public ConteudoDetalheDTO buscar(Long id) {
        return ConteudoDetalheDTO.from(encontrar(id));
    }

    public ConteudoDetalheDTO criar(ConteudoForm form, String email) {
        var conteudo = repository.saveAndFlush(new Conteudo(form, admin(email)));
        return ConteudoDetalheDTO.from(conteudo);
    }

    public ConteudoDetalheDTO atualizar(Long id, ConteudoForm form, String email) {
        var conteudo = encontrarRascunho(id, "Volte o conteúdo para rascunho antes de editar");
        conteudo.atualizar(form, admin(email));
        repository.flush();
        return ConteudoDetalheDTO.from(conteudo);
    }

    public ConteudoDetalheDTO publicar(Long id, String email) {
        var conteudo = encontrar(id);
        conteudo.publicar(admin(email));
        return ConteudoDetalheDTO.from(conteudo);
    }

    public ConteudoDetalheDTO despublicar(Long id, String email) {
        var conteudo = encontrar(id);
        conteudo.despublicar(admin(email));
        return ConteudoDetalheDTO.from(conteudo);
    }

    public void excluir(Long id) {
        repository.delete(encontrarRascunho(id, "Volte o conteúdo para rascunho antes de excluir"));
    }

    private Conteudo encontrarRascunho(Long id, String mensagem) {
        var conteudo = encontrar(id);
        if (conteudo.isPublicado()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, mensagem);
        }
        return conteudo;
    }

    private Conteudo encontrar(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private AdminUser admin(String email) {
        return adminRepository.findByEmail(email).orElseThrow();
    }
}
