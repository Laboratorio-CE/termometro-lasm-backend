package br.com.laboratorioce.termometro.pagina;

import java.time.OffsetDateTime;

public record PaginaDTO(String chave, String titulo, String corpo, OffsetDateTime atualizadoEm) {

    public static PaginaDTO from(Pagina p) {
        return new PaginaDTO(p.getChave(), p.getTitulo(), p.getCorpo(), p.getAtualizadoEm());
    }
}
