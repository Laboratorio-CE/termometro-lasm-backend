package br.com.laboratorioce.termometro.checkin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CheckinForm(
        @NotNull(message = "é obrigatório")
        @Min(value = 1, message = "deve estar entre 1 e 5")
        @Max(value = 5, message = "deve estar entre 1 e 5")
        Short nivel,
        List<@Pattern(regexp = "sono|estudo|trabalho|relacoes|saude|dinheiro", message = "contexto inválido") String> contextos,
        @Size(max = 500, message = "máximo de 500 caracteres") String comentario) {
}
