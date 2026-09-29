package br.com.laboratorioce.termometro.conteudo.dto;

import br.com.laboratorioce.termometro.conteudo.Conteudo;
import java.time.OffsetDateTime;

/** Visão pública: na listagem o corpo vem nulo, só a leitura traz o texto. */
public record ConteudoPublicoDTO(Long id, String slug, String titulo, String categoria, String resumo, Short minutos,
        OffsetDateTime publicadoEm, String corpo) {

    public static ConteudoPublicoDTO resumo(Conteudo c) {
        return new ConteudoPublicoDTO(c.getId(), c.getSlug(), c.getTitulo(), c.getCategoria(), c.getResumo(),
                c.getMinutos(), c.getPublicadoEm(), null);
    }

    public static ConteudoPublicoDTO completo(Conteudo c) {
        return new ConteudoPublicoDTO(c.getId(), c.getSlug(), c.getTitulo(), c.getCategoria(), c.getResumo(),
                c.getMinutos(), c.getPublicadoEm(), c.getCorpo());
    }
}
