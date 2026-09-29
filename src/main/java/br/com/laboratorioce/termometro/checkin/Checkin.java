package br.com.laboratorioce.termometro.checkin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "checkin")
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Short nivel;

    private String contextos;

    private String comentario;

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

    protected Checkin() {
    }

    public Checkin(CheckinForm form) {
        this.nivel = form.nivel();
        this.contextos = form.contextos() == null || form.contextos().isEmpty() ? null
                : String.join(",", form.contextos().stream().distinct().toList());
        this.comentario = form.comentario() == null || form.comentario().isBlank() ? null : form.comentario();
        this.criadoEm = OffsetDateTime.now();
    }
}
