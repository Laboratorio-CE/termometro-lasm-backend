package br.com.laboratorioce.termometro.pagina;

import br.com.laboratorioce.termometro.admin.AdminUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "pagina")
public class Pagina {

    @Id
    private String chave;

    private String titulo;

    @Column(nullable = false)
    private String corpo;

    @Column(name = "atualizado_em", nullable = false)
    private OffsetDateTime atualizadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atualizado_por")
    private AdminUser atualizadoPor;

    protected Pagina() {
    }

    public void atualizar(String titulo, String corpo, AdminUser admin) {
        this.titulo = titulo;
        this.corpo = corpo;
        this.atualizadoEm = OffsetDateTime.now();
        this.atualizadoPor = admin;
    }

    public String getChave() {
        return chave;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCorpo() {
        return corpo;
    }

    public OffsetDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

}
