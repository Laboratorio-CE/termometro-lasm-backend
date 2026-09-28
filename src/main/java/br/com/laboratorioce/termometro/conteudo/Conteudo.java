package br.com.laboratorioce.termometro.conteudo;

import br.com.laboratorioce.termometro.admin.AdminUser;
import br.com.laboratorioce.termometro.conteudo.dto.ConteudoForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "conteudo")
public class Conteudo {

    public static final String RASCUNHO = "RASCUNHO";
    public static final String PUBLICADO = "PUBLICADO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String categoria;

    private String resumo;

    @Column(nullable = false)
    private String corpo;

    private Short minutos;

    @Column(nullable = false)
    private String status;

    @Column(name = "publicado_em")
    private OffsetDateTime publicadoEm;

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private OffsetDateTime atualizadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atualizado_por")
    private AdminUser atualizadoPor;

    protected Conteudo() {
    }

    public Conteudo(ConteudoForm form, AdminUser admin) {
        this.criadoEm = OffsetDateTime.now();
        this.status = RASCUNHO;
        atualizar(form, admin);
    }

    public void atualizar(ConteudoForm form, AdminUser admin) {
        this.slug = form.slug();
        this.titulo = form.titulo();
        this.categoria = form.categoria();
        this.resumo = form.resumo();
        this.corpo = form.corpo();
        this.minutos = form.minutos();
        registrarAlteracao(admin);
    }

    public void publicar(AdminUser admin) {
        this.status = PUBLICADO;
        registrarAlteracao(admin);
        if (publicadoEm == null) {
            this.publicadoEm = atualizadoEm;
        }
    }

    public void despublicar(AdminUser admin) {
        this.status = RASCUNHO;
        registrarAlteracao(admin);
    }

    public boolean isPublicado() {
        return PUBLICADO.equals(status);
    }

    private void registrarAlteracao(AdminUser admin) {
        this.atualizadoEm = OffsetDateTime.now();
        this.atualizadoPor = admin;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getResumo() {
        return resumo;
    }

    public String getCorpo() {
        return corpo;
    }

    public Short getMinutos() {
        return minutos;
    }

    public String getStatus() {
        return status;
    }

    public OffsetDateTime getPublicadoEm() {
        return publicadoEm;
    }

    public OffsetDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public AdminUser getAtualizadoPor() {
        return atualizadoPor;
    }

}
