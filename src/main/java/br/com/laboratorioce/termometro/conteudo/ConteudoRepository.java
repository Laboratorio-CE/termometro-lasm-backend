package br.com.laboratorioce.termometro.conteudo;

import java.util.List;
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

}
