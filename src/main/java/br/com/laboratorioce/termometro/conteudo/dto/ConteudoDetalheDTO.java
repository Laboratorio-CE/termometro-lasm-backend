package br.com.laboratorioce.termometro.conteudo.dto;

import br.com.laboratorioce.termometro.conteudo.Conteudo;
import java.time.OffsetDateTime;

public record ConteudoDetalheDTO(Long id, String slug, String titulo, String categoria, String resumo, String corpo,
        Short minutos, String status, OffsetDateTime publicadoEm, OffsetDateTime atualizadoEm, String revisor) {

    public static ConteudoDetalheDTO from(Conteudo c) {
        return new ConteudoDetalheDTO(c.getId(), c.getSlug(), c.getTitulo(), c.getCategoria(), c.getResumo(),
                c.getCorpo(), c.getMinutos(), c.getStatus(), c.getPublicadoEm(), c.getAtualizadoEm(),
                c.getAtualizadoPor() == null ? null : c.getAtualizadoPor().getNome());
    }
}
