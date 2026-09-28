package br.com.laboratorioce.termometro.pagina;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PaginaForm(@Size(max = 200) String titulo, @NotNull String corpo) {
}
