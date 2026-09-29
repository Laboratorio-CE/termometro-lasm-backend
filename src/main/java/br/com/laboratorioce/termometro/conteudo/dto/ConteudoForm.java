package br.com.laboratorioce.termometro.conteudo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ConteudoForm(
        @NotBlank @Size(max = 200) String titulo,
        @NotBlank @Size(max = 160)
        @Pattern(regexp = "[a-z0-9]+(-[a-z0-9]+)*", message = "use letras minúsculas, números e hífen")
        String slug,
        @NotNull
        @Pattern(regexp = "Ansiedade|Sono|Rotina|Relações|Autocuidado|Quando buscar ajuda", message = "categoria inválida")
        String categoria,
        @Size(max = 300) String resumo,
        @NotBlank String corpo,
        @Min(1) Short minutos) {
}
