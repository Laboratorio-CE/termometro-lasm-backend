package br.com.laboratorioce.termometro.conteudo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConteudoRepository extends JpaRepository<Conteudo, Long> {

    @Query("""
            select c from Conteudo c left join fetch c.atualizadoPor
            where (:status is null or c.status = :status)
              and (:categoria is null or c.categoria = :categoria)
            order by c.atualizadoEm desc
            """)
    List<Conteudo> listar(String status, String categoria);

    @Query("""
            select c from Conteudo c
            where c.status = 'PUBLICADO'
              and (:categoria is null or c.categoria = :categoria)
            order by c.publicadoEm desc
            """)
    List<Conteudo> listarPublicados(String categoria);

    Optional<Conteudo> findBySlugAndStatus(String slug, String status);

}
