package br.com.laboratorioce.termometro.conteudo.dto;

import br.com.laboratorioce.termometro.conteudo.Conteudo;
import java.time.OffsetDateTime;

public record ConteudoResumoDTO(Long id, String slug, String titulo, String categoria, String status,
        OffsetDateTime publicadoEm, OffsetDateTime atualizadoEm, String revisor) {

    public static ConteudoResumoDTO from(Conteudo c) {
        return new ConteudoResumoDTO(c.getId(), c.getSlug(), c.getTitulo(), c.getCategoria(), c.getStatus(),
                c.getPublicadoEm(), c.getAtualizadoEm(),
                c.getAtualizadoPor() == null ? null : c.getAtualizadoPor().getNome());
    }
}
